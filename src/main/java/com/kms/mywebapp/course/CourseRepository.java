package com.kms.mywebapp.course;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Integer>, CustomCourseRepository {
    @Query(value = "select *\n" +
            "from course\n" +
            "where course.id in (" +
            "select sc.course_id " +
            "from student_course sc " +
            "group by sc.course_id " +
            "having count(*) > 2);", nativeQuery = true)
    List<Course> findCoursesHaveMoreThan2Students();
}
