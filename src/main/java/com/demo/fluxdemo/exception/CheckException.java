package com.demo.fluxdemo.exception;

import lombok.Data;

@Data
public class CheckException extends RuntimeException {

    private String fieldName;
    private String fieldValue;

    public CheckException(String fieldName, String fieldValue) {
        super();
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
