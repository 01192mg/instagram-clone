package com.example.clone_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private boolean success;
    private T data;
    private Error error;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, null);
    }

    public static <T> ResponseDto<T> fail(String code, String message) {
        return new ResponseDto<>(false, null, new Error(code, message));
    }

    @Getter
    @AllArgsConstructor
    private static class Error {
        private String code;
        private String message;
    }
}
