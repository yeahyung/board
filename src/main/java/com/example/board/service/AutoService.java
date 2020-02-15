package com.example.board.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Iterator;

@Service
public class AutoService {

    // Java에서 cmd 명령어 실행
    public static String execCommand(String[] cmd){
        Process process;
        BufferedReader bufferedReader;
        StringBuffer readBuffer;

        try{
            process = Runtime.getRuntime().exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
            String line = null;
            readBuffer = new StringBuffer();

            while((line = bufferedReader.readLine()) != null){
                readBuffer.append(line);
                readBuffer.append("\n");
            }
            return readBuffer.toString();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    // 웹페이지에서 upload한 이미지 image directory에 다운
    public String fileUpload(MultipartHttpServletRequest request){
        String s = System.getProperty("user.dir");

        Iterator<String> itr = request.getFileNames();

        String filePath = s + "/images";
        File dir = new File(filePath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        while(itr.hasNext()){
            MultipartFile mpf = request.getFile(itr.next());
            String originalFilename = mpf.getOriginalFilename();
            System.out.println(originalFilename);
            try{
                mpf.transferTo(new File(filePath+"/"+originalFilename));
            }catch(Exception e){
                System.out.println(e);
                e.printStackTrace();
            }
        }

        return "OK";
    }

    // 폴더에 있는 이미지 목록 제공
    public String getFileList(){
        String s = System.getProperty("user.dir");
        String filePath = s + "/images";
        String result = "";
        File dir = new File(filePath);
        if(!dir.exists()){
            return "empty";
        }
        File[] fileList = dir.listFiles();
        if(fileList.length > 0){
            for(File temp : fileList){
                result += temp.getName() + "\n";
            }
            return result;
        }

        return "empty";
    }
}
