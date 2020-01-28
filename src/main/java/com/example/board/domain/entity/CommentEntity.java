package com.example.board.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor()
@Getter
@Entity
@Table(name = "comment")
public class CommentEntity extends TimeEntity {

    // Comment Table에 어떤 정보들이 필요할까?
    // id(PK), postNo(FK), writer, (password), comment, createdDate, modifiedDate

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=10)
    private Long postNo;

    @Column(length=10, nullable = false)
    private String writer;

    /*@Column(length=10, nullable = false)
    private String password;*/

    @Column(length=100, nullable=false)
    private String comment;

    @Builder
    public CommentEntity(Long id, Long postNo, String comment, String writer){
        this.id = id;
        this.postNo = postNo;
        this.writer = writer;
        // this.password = password;
        this.comment = comment;
    }


}
