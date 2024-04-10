package com.library.demo.exeption;

public class NotFoundExecption extends  RuntimeException{
    public NotFoundExecption() {
    }

    public NotFoundExecption(String message, Throwable cause) {
        super(message, cause);
    }
}
