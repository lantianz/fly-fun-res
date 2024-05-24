package com.ltz.flyfun.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    String  status;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>("success", data);
    }

    public static <T> Result<T> fail(T data) {
        return new Result<>("fail", data);
    }
}
