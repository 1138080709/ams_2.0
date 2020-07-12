package com.ams.common.exception;

/**
 * @Author: WuWeiquan
 * @Date: 2020/6/13 1:42
 */
public class FileDeleteException extends RuntimeException {

    private static final long serialVersionUID = 3712153240049250935L;

    private String message;
    
    public FileDeleteException() {
        super();
    }

    public FileDeleteException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
