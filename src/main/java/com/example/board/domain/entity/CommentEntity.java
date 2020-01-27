package com.example.board.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor()
@Getter
@Entity
@Table(name = "comment")
public class CommentEntity {

    // Comment Table에 어떤 정보들이 필요할까?
    // id(PK), postNo(FK), writer, (password), comment, createdDate, modifiedDate

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=10, nullable = false)
    private String writer;

    @Column(length=100, nullable=false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    public String content;

    @Builder
    public CommentEntity(Long id, String title, String content, String writer){
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
