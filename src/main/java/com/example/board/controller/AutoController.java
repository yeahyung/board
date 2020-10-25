package com.example.board.controller;

import com.example.board.dto.TerminalRequestDto;
import com.example.board.service.AutoService;
import com.example.board.util.FileHandler;
import com.example.board.vo.request.ImgAugRequestVo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;

@Controller
@AllArgsConstructor
public class AutoController {
    private static final Logger LOG = LoggerFactory.getLogger(AutoController.class);

    private AutoService autoService;

    @Autowired
    FileHandler fileHandler;

    // 이미지 upload 기본 html
    @RequestMapping("/test")
    public String test() {
        // 사용자에게 현재 폴더에 있는 이미지 목록 제공
        // mac 기준 /Users/user/ncp/board/images
        /*String result = autoService.getFileList();
        LOG.warn(result);
        */

        return "home";
    }

    // 이미지 upload
    // console -> 서버(로컬)로 이미지 upload
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String fileUpload(MultipartHttpServletRequest request) {
        return autoService.fileUpload(request);
    }

    // image augmentation 요청 & 결과 값 다운로드
    @RequestMapping(value = "/imgAug", method = RequestMethod.POST)
    @ResponseBody
    public byte[] imageAugmentation(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException{
        return autoService.imgAug(request, response);
    }

    // 이미지 download test
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public byte[] fileDownload(HttpServletResponse response) throws IOException {
        return autoService.fileDownload(response);
    }


    /*
    이미지 training
    사용자는 자신의 train 폴더(images + labels), data.yaml 파일을 zip file로 압축하여 전달한다.
    train - images - *.jpg
    train - labels - *.txt
    data.yaml

    zip file을.. 그냥 전달하나?
    zip file은 어떻게 전달? sftp?

    전달해서 script 내에서 python code로 train.txt file 작성 및 valid 폴더 생성?
    flask app을 띄워놓는 게 나은가?
     */
    @RequestMapping(value = "/train", method = RequestMethod.POST)
    @ResponseBody
    public String trainImages(MultipartHttpServletRequest request){
        return autoService.trainImages(request);
    }


    // Java에서 cmd 실행
    @PostMapping("/terminal")
    @ResponseBody
    public String terminalTest(@RequestBody TerminalRequestDto requestDto) { // 추후에는 request에 명령어를 담아서 오자.
        //String result = autoService.execCommand(requestDto.getTerminalCommand());
        return null;
    }

    // Java에서 teminal -> python 실행
    @GetMapping("/python/{file}")
    @ResponseBody
    public String python(@PathVariable String file) { // 추후에는 request에 명령어를 담아서 오자.
        String cmd = "python3 /Users/user/ncp/board/" + file;
        //String result = autoService.execCommand(cmd);
        return null;
    }

    // script file get
    @GetMapping("/get/{file}")
    @ResponseBody
    public String getFile(@PathVariable String file) { // 추후에는 request에 명령어를 담아서 오자.
        return fileHandler.readResourceFileToString("static/python/" + file + ".sh");
    }
}
