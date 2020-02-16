package com.example.board.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageSourceUtil {
    private static MessageSource messageSource;

    @Autowired
    public MessageSourceUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public static String getMessage(String headerValue, Locale locale) {
        return messageSource.getMessage(headerValue, null, locale);
    }

    public static String getMessage(String headerValue, Object[] arg,  Locale locale) {
        return messageSource.getMessage(headerValue, arg, locale);
    }
}
