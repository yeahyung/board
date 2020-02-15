package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.dto.CommentDto;
import com.example.board.service.AutoService;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import com.example.board.vo.request.ImgAugRequestVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@AllArgsConstructor
public class BoardController {
    private BoardService boardService;
    private CommentService commentService;

    @GetMapping("/")
    public String list(Model model){
        List<BoardDto> boardList = boardService.getBoardList();
        //List<BoardEntity> boardList = boardService.getBoardList();
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
        List<CommentDto> commentList = commentService.getCommentList(no);

        model.addAttribute("boardDto", boardDto);
        model.addAttribute("commentList", commentList);

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

        return "redirect:/";
    }

    @PostMapping(value = "/post/delete/{no}")
    public String deletePost(@PathVariable("no") Long no){
        boardService.deletePost(no);

        return "redirect:/";
    }

    @RequestMapping(value = "/comment/{no}", method= {RequestMethod.GET, RequestMethod.POST})
    public String writeComment(@PathVariable Long no, CommentDto commentDto){
        //System.out.println("글 번호: " + no);
        //System.out.println(commentDto.getComment());
        commentDto.setPostNo(no);
        commentService.saveComment(commentDto);

        return "redirect:/";
    }

    @PostMapping(value = "/comment/delete/{no}")
    public String deleteComment(@PathVariable("no") Long no){
        commentService.deleteComment(no);

        return "redirect:/";
    }
}
