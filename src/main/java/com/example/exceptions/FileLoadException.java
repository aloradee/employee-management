package com.example.exceptions;

public class FileLoadException extends Exception{
    public FileLoadException(String message) {
        super(message);
    }
    public FileLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
