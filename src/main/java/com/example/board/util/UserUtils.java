package com.example.board.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserUtils {

    public static String getUserIp(){
        HttpServletRequest request = ServletRequestUtil.getRequest();
        // 일반적인 IP 경우
        String ip = request.getRemoteAddr() ;

        // 포워딩 헤더인 경우
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("X-FORWARDED-FOR");
        }

        //웹로직 서버일 경우
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        //proxy 환경일 경우
        if (ip == null || ip.length() == 0) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        return ip;
    }
}
