package com.example.board.controller;

import com.example.board.service.AutoService;
import com.example.board.vo.request.ImgAugRequestVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;

@Controller
@AllArgsConstructor
public class AutoController {
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
        String[] cmd = {"ifconfig"};
        String result = autoService.execCommand(cmd);
        return result;
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
    public String imageAugmentation(MultipartHttpServletRequest request) { // 추후에 ImgAugRequestVo request로 수정해야하는데 FE에서 어떻게 전달해야하는지 모르겠
        Iterator<String> itr = request.getFileNames(); // request.getRequest().getFileNames();
        while (itr.hasNext()) {
            MultipartFile mpf = request.getFile(itr.next()); // request.getRequest().getFile(itr.next());
            String originalFilename = mpf.getOriginalFilename();
            System.out.println(originalFilename);
        }
        return null;
    }

}
