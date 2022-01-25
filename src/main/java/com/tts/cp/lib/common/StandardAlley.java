package com.tts.cp.lib.common;

import java.io.Serializable;

public class StandardAlley<T> implements Serializable {
    private static final long serialVersionUID = -5926710562826960456L;
    private String statusCode;
    private String statusDesc;
    private Boolean success;
    private T data;

    public String getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDesc() {
        return this.statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public StandardAlley(String statusCode, String statusDesc, Boolean success, T data) {
        this.statusCode = statusCode;
        this.statusDesc = statusDesc;
        this.success = success;
        this.data = data;
    }

    public StandardAlley() {
    }
}