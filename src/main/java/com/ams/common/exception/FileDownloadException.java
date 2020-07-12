package com.ams.common.exception;

/**
 * @Author: WuWeiquan
 * @Date: 2020/6/13 1:08
 */
public class FileDownloadException extends RuntimeException {

    private static final long serialVersionUID = 3712153240006350935L;
    
    private String message;
    
    public FileDownloadException() {
        super();
    }

    public FileDownloadException(String message) {
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
