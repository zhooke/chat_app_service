package com.tensua.enums;

import lombok.Getter;

/**
 * @author haoxr
 * @description TODO
 * @createTime 2021/6/5 17:57
 */

public enum PwdEncoderTypeEnum {

    BCRYPT("{bcrypt}","BCRYPT加密"),
    NOOP("{noop}","无加密明文");

    @Getter
    private String prefix;

    PwdEncoderTypeEnum(String prefix, String desc){
        this.prefix=prefix;
    }

}
