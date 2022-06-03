package com.kms.mywebapp.course;

import java.util.List;

public interface CustomCourseRepository {
    List<Course> findCourseHaveMoreThan2StudentsLivingInHCMC();
}
