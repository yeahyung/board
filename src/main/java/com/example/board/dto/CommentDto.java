package com.example.board.dto;

import com.example.board.domain.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentDto {

    // Comment Table에 어떤 정보들이 필요할까?
    // id(PK), postNo(FK), writer, (password), comment, createdDate, modifiedDate

    private Long id;
    private Long postNo;
    private String writer;
    private String comment;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    // Dto를 entity로 변경
    public CommentEntity toEntity(){
        CommentEntity build = CommentEntity.builder()
                .id(id)
                .postNo(postNo)
                .writer(writer)
                .comment(comment)
                .build();
        return build;
    }

    @Builder
    public CommentDto(Long id, Long postNo, String writer, String comment, LocalDateTime createdDate, LocalDateTime modifiedDate){
        this.id = id;
        this.postNo = postNo;
        this.writer = writer;
        this.comment = comment;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
