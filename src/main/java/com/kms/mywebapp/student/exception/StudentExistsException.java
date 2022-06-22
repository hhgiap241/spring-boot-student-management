package com.kms.mywebapp.student.exception;

/**
 * @author : giaphoang
 * @mailto : hoanghuugiap241@gmail.com
 * @created : 6/22/2022, Wednesday
 * @project: MyWebApp
 **/
public class StudentExistsException extends RuntimeException {
    public StudentExistsException(String message) {
        super(message);
    }
}
