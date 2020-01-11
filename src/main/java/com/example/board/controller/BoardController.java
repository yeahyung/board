package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.service.BoardService;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {
    private BoardService boardService;

    @GetMapping("/test")
    public String test(){
        return "Hello from develop1 & change from remote";
    }

    @GetMapping("/")
    public String list(Model model){
        List<BoardDto> boardList = boardService.getBoardList();

        model.addAttribute("boardList", boardList);
        return "/board/list.html";
    }

    @GetMapping("/post")
    public String write(){
        return "/board/write.html";
    }

    @PostMapping("/post")
    public String write(BoardDto boardDto){
        boardService.savePost(boardDto);

        return "redirect:/";
    }

    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model){
        BoardDto boardDto = boardService.getPost(no);

        model.addAttribute("boardDto", boardDto);
        return "/board/detail.html";
    }

    @GetMapping(value = "/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model){
        BoardDto boardDto = boardService.getPost(no);

        model.addAttribute("boardDto", boardDto);
        return "/board/update.html";
    }

    @RequestMapping(value = "/post/editted/{no}", method= {RequestMethod.GET, RequestMethod.POST})
    // @PostMapping("/post/edditted/{no}")
    public String update(BoardDto boardDto){
        boardService.savePost(boardDto);
        System.out.println("edit");
        return "redirect:/";
    }

    @PostMapping(value = "/post/delete/{no}")
    public String delete(@PathVariable("no") Long no){
        boardService.deletePost(no);

        return "redirect:/";
    }

}
