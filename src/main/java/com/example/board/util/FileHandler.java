package com.example.board.util;

import com.example.board.controller.AutoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;

@Component
public class FileHandler {
    private static final Logger LOG = LoggerFactory.getLogger(FileHandler.class);

    public String readResourceFileToString(String fileName) {
        LOG.warn(fileName);
        InputStream is = this.getClass().getResourceAsStream("/" + fileName);

        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();

        try {
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\n');
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
    public String fileToBase64String(String fileString) {
        return Base64.getEncoder().encodeToString(fileString.getBytes());
    }
}
