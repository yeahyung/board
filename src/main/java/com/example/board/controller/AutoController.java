package com.example.board.controller;

import com.example.board.dto.TerminalRequestDto;
import com.example.board.service.AutoService;
import com.example.board.vo.request.ImgAugRequestVo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    // 이미지 upload 기본 html
    @RequestMapping("/test")
    public String test() {
        // 사용자에게 현재 폴더에 있는 이미지 목록 제공
        // mac 기준 /Users/user/ncp/board/images
        /*String result = autoService.getFileList();
        LOG.warn(result);
        */

        return "/project/home.html";
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

    // Java에서 cmd 실행
    @PostMapping("/terminal")
    @ResponseBody
    public String terminalTest(@RequestBody TerminalRequestDto requestDto) { // 추후에는 request에 명령어를 담아서 오자.
        String result = autoService.execCommand(requestDto.getTerminalCommand());
        return result;
    }

    // Java에서 teminal -> python 실행
    @GetMapping("/python/{file}")
    @ResponseBody
    public String python(@PathVariable String file) { // 추후에는 request에 명령어를 담아서 오자.
        String cmd = "python3 /Users/user/ncp/board/" + file;
        String result = autoService.execCommand(cmd);
        return result;
    }

    // 이미지 download test
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public byte[] fileDownload(HttpServletResponse response) throws IOException {
        return autoService.fileDownload(response);
    }
}
