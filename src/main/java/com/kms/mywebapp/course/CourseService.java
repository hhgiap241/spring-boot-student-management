package com.kms.mywebapp.course;

import com.kms.mywebapp.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public List<Course> listAll(){
        return (List<Course>)courseRepository.findAll();
    }
    public void insertCourse(Course course){
        courseRepository.save(course);
    }
    public void deleteCourse(Integer id){
        // delete course from database
        Course course = courseRepository.findById(id).get();
        for (Student student: course.getStudents()) {
            student.getCourses().remove(course);
        }
        courseRepository.deleteById(id);
    }
}
