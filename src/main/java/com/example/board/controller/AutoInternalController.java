package com.example.board.controller;

import com.example.board.service.ExecuteScriptService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.UnknownHostException;

@Controller
@AllArgsConstructor
public class AutoInternalController {

    @Autowired
    ExecuteScriptService executeScriptService;

    // confirmImageAugmentation
    // image augmentation 시작

    // Java에서 cmd 실행 test
    @RequestMapping("/pythonTest")
    @ResponseBody
    public String pythonTest() { // 추후에는 request에 명령어를 담아서 오자.
        executeScriptService.executeRemoteCommand();
        return "OK";
    }
}
