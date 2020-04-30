package com.example.board.controller;

import com.example.board.service.AutoService;
import com.example.board.vo.request.ImgAugRequestVo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
        String result = autoService.getFileList();
        System.out.println(result);
        return "/project/home.html";
    }

    // Java에서 cmd 실행 test
    @RequestMapping("/terminal")
    @ResponseBody
    public String terminalTest() { // 추후에는 request에 명령어를 담아서 오자.
        String cmd = "ifconfig";
        String result = autoService.execCommand(cmd);
        return result;
    }

    // Java에서 terminal -> python 실
    @RequestMapping("/python")
    @ResponseBody
    public String python() { // 추후에는 request에 명령어를 담아서 오자.
        LOG.warn("이미지 Augmentation 실행");
        String cmd = "python3 /Users/yeahyungbin/board/src/main/resources/static/python/test.py";
        String result = autoService.execCommand(cmd);
        return result;
    }

    // 이미지 download test
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public byte[] fileDownload(HttpServletResponse response) throws IOException {
        return autoService.fileDownload(response);
    }

    // 이미지 upload test
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String fileUpload(MultipartHttpServletRequest request) {
        return autoService.fileUpload(request);
    }

    // image augmentation 요청
    @RequestMapping(value = "/imgAug", method = RequestMethod.POST)
    @ResponseBody
    public byte[] imageAugmentation(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException{
        return autoService.imgAug(request, response);
    }
}
