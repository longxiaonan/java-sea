package com.iee.webbase.common;

/**
 * 业务异常，在业务处理过程中，明确已知的异常
 *
 * @author dadiyang
 * @since 2019-06-02
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
