package com.kien.hexagonal_boilerplate.infrastructure.common;



import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BadResponse {

    private Boolean success;
    private Integer code;
    private String message;
    private List<Error> errors;

    @Data
    @Builder
    public static class Error {

        private Integer code;
        private String message;

    }

}
