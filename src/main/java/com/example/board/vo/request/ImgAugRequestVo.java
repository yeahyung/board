package com.example.board.vo.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Data
public class ImgAugRequestVo {
    // web 에서 이미지 선택해서 augmentation 해달라고 할 때 어떤 정보들이 필요할까
    MultipartHttpServletRequest request;
}
