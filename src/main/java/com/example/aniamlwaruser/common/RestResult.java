package com.example.aniamlwaruser.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestResult<T> {
    private String status;
    private T data;
}
