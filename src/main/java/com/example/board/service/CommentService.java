package com.example.board.service;

import com.example.board.dto.BoardDto;
import com.example.board.dto.CommentDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
public class CommentService {

    @Transactional
    public Long saveComment(CommentDto commentDto){
       //System.out.println(boardDto.getContent());
       // return boardRepository.save(boardDto.toEntity()).getId();
        return null;
    }

    @Transactional
    //transactional의 의미는 뭐지?
    public List<CommentDto> getComment(){
        // 댓글 가져오기
        return null;
    }

    @Transactional
    //transactional의 의미는 뭐지?
    public void deleteComment(Long id){
        // 댓글 삭제
    }
}
