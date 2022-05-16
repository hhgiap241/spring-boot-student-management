package com.kms.mywebapp.student;

public class UserNotFoundException extends Throwable{
    public UserNotFoundException(String message) {
        super(message);
    }
}
