package com.example.board.service;

import com.example.board.constant.HistoryCode;
import com.example.board.entity.ImgAugHistory;
import com.example.board.repository.ImgAugHistoryRepository;
import com.example.board.util.MessageSourceUtil;
import com.example.board.vo.request.ImgAugRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Locale;

@Service
public class AutoHistoryService {

    @Autowired
    ImgAugHistoryRepository imgAugHistoryRepository;

    public void insertHistory(HistoryCode historyCode, String memberIp, int fileCount){
        String message = historyCode.getCode();

        ImgAugHistory imgAugHistory = new ImgAugHistory();
        imgAugHistory.setMessage(message);
        imgAugHistory.setClientIp(memberIp);
        imgAugHistory.setActionDate(new Date());
        imgAugHistory.setActionType(historyCode.getActionType());
        imgAugHistory.setFileCount(fileCount);

        insertHistoryMessage(imgAugHistory);
    }

    public void insertHistoryMessage(ImgAugHistory request){
        if(request != null){
            imgAugHistoryRepository.save(request);
        }
    }
}
