package com.kms.mywebapp.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping(value = "/courses")
    public String showCourseList(Model model){
        List<Course> courseList = (List<Course>) courseRepository.findAll();
        model.addAttribute("courseList", courseList);
        return "courses";
    }
    @GetMapping(value = "/courses/add")
    public String addCourse(Model model){
        model.addAttribute("course", new Course());
        model.addAttribute("pageTitle", "Add New Course");
        return "add_course_form";
    }
    @PostMapping(value = "/courses/add")
    public String addCourse(Course course, RedirectAttributes redirectAttributes){
        courseRepository.save(course);
        redirectAttributes.addFlashAttribute("messages", "Course added successfully");
        return "redirect:/courses";
    }
}
