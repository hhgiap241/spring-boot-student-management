package com.kms.mywebapp.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/courses")
    public String showCourseList(Model model){
        List<Course> courseList = courseService.listAll();
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
        courseService.insertCourse(course);
        redirectAttributes.addFlashAttribute("messages", "Course added successfully");
        return "redirect:/courses";
    }
    @GetMapping(value = "/courses/delete/{id}")
    public String deleteCourse(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        courseService.deleteCourse(id);
        redirectAttributes.addFlashAttribute("messages", "Course deleted successfully");
        return "redirect:/courses";
    }
}
