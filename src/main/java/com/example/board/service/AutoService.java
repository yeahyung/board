package com.example.board.service;

import com.example.board.constant.HistoryCode;
import com.example.board.controller.AutoController;
import com.example.board.dto.TerminalRequestDto;
import com.example.board.util.ServletRequestUtil;
import com.example.board.util.UserUtils;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
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

    // 웹페이지에서 upload한 이미지 image directory에 다운
    // /Users/user/download/imgUpload/filename 으로 저
    public String fileUpload(MultipartHttpServletRequest request){
        Iterator<String> itr = request.getFileNames();

        // member 별로 downloadPath 수정, /imgUpload/memberNo 같이
        String downloadPath = "/Users/user/Downloads/imgUpload/";
        File downloadDir = new File(downloadPath);
        if(!downloadDir.exists())
            downloadDir.mkdirs();

        while(itr.hasNext()){
            MultipartFile mpf = request.getFile(itr.next());
            String originalFilename = mpf.getOriginalFilename();
            LOG.warn(originalFilename);
            try{
                mpf.transferTo(new File(downloadPath + originalFilename));
            }catch(Exception e){
                LOG.warn(String.valueOf(e));
                e.printStackTrace();
            }
        }

        return "OK";
    }

    // 폴더에 있는 이미지 목록 제공
    // /Users/user/ncp/board/images 폴더 내에 있는 파일들 목록 반환
    public String getFileList(){
        String s = System.getProperty("user.dir");
        String filePath = s + "/images";
        StringBuilder result = new StringBuilder();

        File dir = new File(filePath);

        if(!dir.exists()){
            return "empty";
        }

        File[] fileList = dir.listFiles();

        if(fileList.length > 0){
            for(File temp : fileList){
                result.append(temp.getName() + "\n");
            }
            return result.toString();
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
        // test.py 실행 --> /Users/user/download/imgUpload 에 있는 image들에 대해서 augmentation 실행 & /Users/user/download/aug 폴더에 저장 & zip 파일 생성 후 aug 폴더 삭제
        LOG.warn("이미지 augmentation을 실행합니다.");
        String cmd = "bash /Users/user/ncp/board/src/main/resources/static/python/script-local.sh";
        String result = execCommand(cmd);

        LOG.warn(result);

        return fileDownload(response);
    }

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
            LOG.error("error");
            System.exit(1);
        }
        return null;
    }

    public byte[] fileDownload(HttpServletResponse response) throws IOException{
        response.setContentType("application/zip");
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"augResult.zip\"");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

        ArrayList<File> files = new ArrayList<>(2);
        files.add(new File("/Users/user/Downloads/myZipFile.zip"));

        LOG.info("download myZipFile.zip");

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

    public String trainImages(MultipartHttpServletRequest request) {

        System.out.println(request);

        Iterator<String> itr = request.getFileNames();

        // 1번 - 관리 서버에서 training
        // member 별로 downloadPath 수정, /trainImage/memberNo 같이
        String downloadPath = "/Users/user/trainImage/";
        File downloadDir = new File(downloadPath);
        if(!downloadDir.exists())
            downloadDir.mkdirs();

        while(itr.hasNext()){
            MultipartFile mpf = request.getFile(itr.next());
            String originalFilename = mpf.getOriginalFilename();
            LOG.warn(originalFilename);
            try{
                mpf.transferTo(new File(downloadPath + originalFilename));
            }catch(Exception e){
                LOG.warn(String.valueOf(e));
                e.printStackTrace();
            }
        }


        // 2번 - 고객 VM 에서 training
        // 고객 VM 에서 train 하기 위해선 zip file 전송 필요 by sftp?? - 일단 local에서만 실행하자. pass
        // /Users/user/trainImage/~~.zip file로 저장
        // unzip file
        // 일단 zip file 내에 images 폴더, labels 폴더, data.yaml 로 분리 / (jpg와 txt 파일 분리는 추후에)
        // python으로?
        /*이미지 training
                train - images - *.jpg
                train - labels - *.txt
                        data.yaml
                전달해서 script 내에서 python code로 train.txt file 작성 및 valid 폴더 생성?
        */



        return "OK";
    }
}
