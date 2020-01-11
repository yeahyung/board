package com.example.board.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor()
@Getter
@Entity
@Table(name = "board")
public class BoardEntity extends TimeEntity {
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
    public BoardEntity(Long id, String title, String content, String writer){
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

}

