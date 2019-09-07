package com.iee.webbase.modelConvertDemo.exception;

public class NotFindUserException extends AbstractException {

    public NotFindUserException(String message) {
        super(AddressErrorCode.NotFindUserErrorCode, message, null);
    }
}
