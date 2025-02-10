package com.kien.hexagonal_boilerplate.infrastructure.util;

import com.kien.hexagonal_boilerplate.infrastructure.common.BadResponse;
import com.kien.hexagonal_boilerplate.infrastructure.common.Response;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class MessageUtil {

    private final MessageSource messageSource;

    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public BadResponse.Error getErrorByFieldErr(FieldError error) {
        String txt = messageSource.getMessage(error.getDefaultMessage(), null, LocaleContextHolder.getLocale());
        String[] parts = txt.split("-");

        return BadResponse.Error.builder()
                .code(Integer.parseInt(parts[0]))
                .message(error.getField() + " " + parts[1])
                .build();
    }

    public BadResponse getBadResponseByMsg(String msg) {
        String txt = messageSource.getMessage(msg, null, LocaleContextHolder.getLocale());
        String[] parts = txt.split("-");

        return BadResponse.builder()
                .success(false)
                .code(Integer.parseInt(parts[0]))
                .message(parts[1])
                .build();
    }

    public Response getResponseByMsg(String msg) {
        String txt = messageSource.getMessage(msg, null, LocaleContextHolder.getLocale());
        String[] parts = txt.split("-");

        return Response.builder()
                .success(true)
                .code(Integer.parseInt(parts[0]))
                .message(parts[1])
                .build();
    }

}
