package com.example.board.service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@Service
public class ExecuteScriptService {
    private static final Logger log = LoggerFactory.getLogger(ExecuteScriptService.class);

    @Value("10.41.167.51")
    private String host;

    public void executeRemoteCommand(){
        log.info("Execute remote command");

        JSch jsch =new JSch();
        Session session = null;
        String privateKeyPath = "/home/nes/.ssh/id_rsa";

        String user="root";
        int port = 22;
        String password = "rornfl123!@#";

        try {
            //jsch.addIdentity(privateKeyPath);
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            // session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
        } catch (JSchException var10) {
            log.error("Failed to create Jsch Session object. : " + var10.getMessage());
        }

        try {
            session.connect();
            Channel channel = session.openChannel("exec");
            ((ChannelExec)channel).setCommand("touch /root/jschTest.txt");
            ((ChannelExec)channel).setPty(false);
            channel.connect();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(channel.getInputStream()));
            String s = null;

            while((s = stdInput.readLine()) != null) {
                log.info(s);
            }

            channel.disconnect();
            session.disconnect();
        } catch (IOException | JSchException var11) {
            log.error("Error durring SSH command execution. Command: " );
        }
    }
}
