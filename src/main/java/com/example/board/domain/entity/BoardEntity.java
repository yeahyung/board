package com.example.board.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

// Entity = 직접적으로 DB와 데이터를 주고 받음, dto는 view와 주고 받음(dto는 수정이 자주 일어남, dto를 사용하지 않을 경우 entity로 DB, view 모두 해야 하는데
// entity에 자주 변경이 일어나면 DB에 치명적인 영향을 끼칠 수 있기 때문에 DB에 접근하는 것과 view에 접근하는 것을 따로 관리하자 --> entity, dto 나눠서 사용
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

