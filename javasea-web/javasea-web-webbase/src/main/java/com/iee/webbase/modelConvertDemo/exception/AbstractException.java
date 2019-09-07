package com.iee.webbase.modelConvertDemo.exception;

public class AbstractException extends RuntimeException {
    protected Long errorCode;
    protected Object data;

    public AbstractException(Long errorCode, String message, Object data, Throwable e) {
        super(message, e);
        this.errorCode = errorCode;
        this.data = data;
    }

    public AbstractException(Long errorCode, String message, Object data) {
        this(errorCode, message, data, null);
    }

    public AbstractException(Long errorCode, String message) {
        this(errorCode, message, null, null);
    }

    public AbstractException(String message, Throwable e) {
        this(null, message, null, e);
    }

    public AbstractException() {

    }

    public AbstractException(Throwable e) {
        super(e);
    }

    public Long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Long errorCode) {
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
