package com.example.board.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "auto_ml_history")
public class ImgAugHistory implements Serializable {
    private static final long serialVersionUID = 5083042984899136213L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Integer idx;

    @Column(name = "action_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date actionDate;

    @Column(name = "message")
    private String message;

    @Column(name = "client_ip")
    private String clientIp;

    @Column(name = "action_type")
    private String actionType;

    @Column(name ="file_count")
    private int fileCount;
}
