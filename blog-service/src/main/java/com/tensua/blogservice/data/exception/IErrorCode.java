package com.tensua.blogservice.data.exception;

/**
 * 封装API的错误码
 */
public interface IErrorCode {
    Integer getCode();

    String getMessage();
}
