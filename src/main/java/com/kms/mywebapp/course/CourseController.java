package com.kms.mywebapp.course;

import com.kms.mywebapp.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping(value = "")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE', 'TEACHER')")
    public String showCourseList(Model model){
        List<Course> courseList = courseService.listAll();
        model.addAttribute("courseList", courseList);
        return "courses";
    }
    @GetMapping(value = "/add")
    public String addCourse(Model model){
        model.addAttribute("course", new Course());
        model.addAttribute("pageTitle", "Add New Course");
        return "add_course_form";
    }
    @PostMapping(value = "/add")
    public String addCourse(Course course, RedirectAttributes redirectAttributes){
        courseService.insertCourse(course);
        redirectAttributes.addFlashAttribute("messages", "Course added successfully");
        return "redirect:/courses";
    }
    @GetMapping(value = "/delete/{id}")
    public String deleteCourse(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        courseService.deleteCourse(id);
        redirectAttributes.addFlashAttribute("messages", "Course deleted successfully");
        return "redirect:/courses";
    }
    @GetMapping(value = "/filter", params = {"value"})
    public String filterStudent(@RequestParam("value") Integer value, Model model) {
//        List<Student> studentList = userService.filterStudent(name);
//        model.addAttribute("listUsers", studentList);
        List<Course> courseList = null;
        switch (value) {
            case 0:
                courseList = courseService.listAll();
                break;
            case 1:
                courseList = courseService.findCoursesHaveMoreThan2Students();
                break;
            case 2:
                courseList = courseService.findCourseHaveMoreThan2StudentsLivingInHCMC();
                break;
        }
        model.addAttribute("courseList", courseList);
        return "courses";
    }
}
