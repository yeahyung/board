package com.example.board.service;

import com.example.board.domain.entity.CommentEntity;
import com.example.board.domain.repository.CommentRepository;
import com.example.board.dto.CommentDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CommentService {
    private CommentRepository commentRepository;

    @Transactional
    public Long saveComment(CommentDto commentDto){
       //System.out.println(commentDto.getComment());
       return commentRepository.save(commentDto.toEntity()).getId();
    }

    @Transactional
    //transactional의 의미는 뭐지?
    public List<CommentDto> getCommentList(Long id){
        // 댓글 가져오기
        List<CommentEntity> commentEntities = commentRepository.findAllByPostNo(id);
        List<CommentDto> commentDtoList = new ArrayList<>();

        for(CommentEntity commentEntity : commentEntities){
            CommentDto commentDto = CommentDto.builder()
                    .id(commentEntity.getId())
                    .postNo(commentEntity.getPostNo())
                    .writer(commentEntity.getWriter())
                    .comment(commentEntity.getComment())
                    .createdDate(commentEntity.getCreatedDate())
                    .build();
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }

    @Transactional
    public void deleteComment(Long id){
        // 댓글 삭제
        commentRepository.deleteById(id);
    }
}
