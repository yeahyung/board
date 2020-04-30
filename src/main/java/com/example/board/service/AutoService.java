package com.example.board.service;

import com.example.board.constant.HistoryCode;
import com.example.board.controller.AutoController;
import com.example.board.util.ServletRequestUtil;
import com.example.board.util.UserUtils;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class AutoService {
    private static final Logger LOG = LoggerFactory.getLogger(AutoController.class);

    @Autowired
    AutoHistoryService autoHistoryService;

    // Java에서 cmd 명령어 실행
    public static String execCommand(String cmd){
        Process process;
        BufferedReader bufferedReader;
        StringBuffer readBuffer;

        try{
            process = Runtime.getRuntime().exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
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
                mpf.transferTo(new File("/Users/yeahyungbin/Downloads/imgUpload/"+originalFilename));
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

    // 전달되는 file list에 대해 img augmentation 요청
    // @Todo
    // 제대로 된 로직이라면, 파일 선택하고 Aug 요청하면 알아서 서버에 저장되서 aug 작업하고, zip file return 해줘야지..
    public byte[] imgAug(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException{
        String res = "OK";

        LOG.warn("이미지를 서버에 저장하였습니다.");
        fileUpload(request);

        /*int fileCount = 0;
        ArrayList<MultipartFile> multipartFileList = new ArrayList<>();
        Iterator<String> itr = request.getFileNames(); // request.getRequest().getFileNames();

        while (itr.hasNext()) {
            MultipartFile mpf = request.getFile(itr.next()); // request.getRequest().getFile(itr.next());
            String originalFilename = mpf.getOriginalFilename();
            int pos = originalFilename.lastIndexOf(".");
            if("jpg".equals(originalFilename.substring(pos+1)) || "jpeg".equals(originalFilename.substring(pos+1))) {
                fileCount++;
                multipartFileList.add(mpf);
            }
        }
        if(fileCount!=0) {
            System.out.println("Img request history 저장");
            autoHistoryService.insertHistory(HistoryCode.IMAGE_AUGMENTATION_REQUEST, UserUtils.getUserIp(), fileCount);
        }
        else{
            System.out.println("jpg 형태의 파일을 선택해주세요");
            res = "ERROR";
        }*/

        // multipartFileList에 대해서 imgAug 실행

        // 이미지 Aug 실행
        LOG.warn("이미지 augmentation을 실행합니다.");
        String cmd = "python3 /Users/yeahyungbin/board/src/main/resources/static/python/test.py";
        String result = execCommand(cmd);

        return fileDownload(response);
    }

    public byte[] fileDownload(HttpServletResponse response) throws IOException{
        response.setContentType("application/zip");
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"test.zip\"");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

        ArrayList<File> files = new ArrayList<>(2);
        files.add(new File("/Users/yeahyungbin/Downloads/myZipFile.zip"));

        //packing files
        for (File file : files) {
            //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fileInputStream = new FileInputStream(file);

            IOUtils.copy(fileInputStream, zipOutputStream);

            fileInputStream.close();
            zipOutputStream.closeEntry();
        }

        if (zipOutputStream != null) {
            zipOutputStream.finish();
            zipOutputStream.flush();
            IOUtils.closeQuietly(zipOutputStream);
        }
        IOUtils.closeQuietly(bufferedOutputStream);
        IOUtils.closeQuietly(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
