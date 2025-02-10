package com.kien.hexagonal_boilerplate.infrastructure.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private Boolean success;
    private Integer code;
    private String message;
    private T data;

}
