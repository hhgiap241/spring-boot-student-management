package com.kms.mywebapp.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

/**
 * @author : giaphoang
 * @mailto : hoanghuugiap241@gmail.com
 * @created : 6/20/2022, Monday
 * @project: MyWebApp
 **/
public enum ApplicationUserPermission {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    BOOK_READ("book:read"),
    BOOK_WRITE("book:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
