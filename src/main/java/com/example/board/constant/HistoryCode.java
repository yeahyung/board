package com.example.board.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum HistoryCode {
    IMAGE_AUGMENTATION_REQUEST("IMAGE_AUGMENTATION_REQUEST", "image"),
    IMAGE_AUGMENTATION_COMPLETE("IMAGE_AUGMENTATION_COMPLETE", "image"),

    NULL(null, null);


    private String code;
    private String actionType;
}
