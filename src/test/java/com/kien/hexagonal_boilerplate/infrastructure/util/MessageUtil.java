package com.kien.hexagonal_boilerplate.infrastructure.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.kien.hexagonal_boilerplate.infrastructure.common.BadResponse;
import com.kien.hexagonal_boilerplate.infrastructure.common.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.FieldError;

import java.util.Locale;

@ExtendWith(MockitoExtension.class)
class MessageUtilTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private MessageUtil messageUtil;

    @BeforeEach
    void setUp() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
    }

    @Test
    void getErrorByFieldErr_ShouldReturnErrorObject() {
        FieldError fieldError = new FieldError("object", "field", "error.code");
        when(messageSource.getMessage(eq("error.code"), any(), any(Locale.class)))
                .thenReturn("400-Bad Request");

        BadResponse.Error error = messageUtil.getErrorByFieldErr(fieldError);

        assertEquals(400, error.getCode());
        assertEquals("field Bad Request", error.getMessage());
    }

    @Test
    void getBadResponseByMsg_ShouldReturnBadResponse() {
        when(messageSource.getMessage(eq("order.product.not.found"), any(), any(Locale.class)))
                .thenReturn("404-Not Found");

        BadResponse response = messageUtil.getBadResponseByMsg("order.product.not.found");

        assertFalse(response.getSuccess());
        assertEquals(404, response.getCode());
        assertEquals("Not Found", response.getMessage());
    }

    @Test
    void getResponseByMsg_ShouldReturnResponse() {
        when(messageSource.getMessage(eq("order.product.update.success"), any(), any(Locale.class)))
                .thenReturn("200-Update Successful");

        Response response = messageUtil.getResponseByMsg("order.product.update.success");

        assertTrue(response.getSuccess());
        assertEquals(200, response.getCode());
        assertEquals("Update Successful", response.getMessage());
    }
}
