package com.kms.mywebapp.student;

import com.kms.mywebapp.book.Book;
import com.kms.mywebapp.book.BookService;
import com.kms.mywebapp.card.StudentIdCard;
import com.kms.mywebapp.course.Course;
import com.kms.mywebapp.course.CourseRepository;
import com.kms.mywebapp.student.exception.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE', 'TEACHER')")
    public String getAllStudents(Model model){
        List<Student> userList = userService.getAllStudents();
        model.addAttribute("listStudent", userList);
        return "students";
    }
//    @GetMapping("/users/dd")
//    @ResponseBody
//    public List<Student> showUserListd(){
//        List<Student> userList = userService.listAll();
//        return userList;
//    }

    @GetMapping("/id-cards")
    public String getAllStudentCards(Model model){
        List<StudentIdCard> cardList = userService.getAllStudentIdCard();
        model.addAttribute("listCards", cardList);
        return "cards";
    }

    @GetMapping("/add")
    public String showAddNewStudentForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Add New Student");
        return "add_student_form";
    }

    @PostMapping("/add")
    public String addNewStudent(Student user, RedirectAttributes redirectAttributes) {
        boolean result = userService.addNewStudent(user);
        String message;
        if(result)
            message = "Student has been saved successfully!";
        else message = "Student has not been saved!";
        redirectAttributes.addFlashAttribute("messages", message);
        return "redirect:/students";
    }
    @GetMapping("/edit/{id}")
    public String showEditStudentForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Student student = userService.getStudentById(id);
            List<Book> bookList = bookService.getAvailableBooks();
            List<Course> courseList = (List<Course>) courseRepository.findAll();
            model.addAttribute("student", student);
            model.addAttribute("bookList", bookList);
            model.addAttribute("courseList", courseList);
            model.addAttribute("pageTitle", "Edit Student (ID: " + id + ")");
            return "update_student_form";
        } catch (StudentNotFoundException e) {
            redirectAttributes.addFlashAttribute("messages", e.getMessage());
            return "update_student_form";
        }
    }
    @PostMapping("/edit/{id}")
    public String editStudent(RedirectAttributes redirectAttributes, @ModelAttribute("student") Student student, @RequestParam("id") Integer id) {
        userService.updateStudentInfo(id, student);
        redirectAttributes.addFlashAttribute("messages", "Student has been updated successfully!");
        return "redirect:/students";
    }
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("messages", "Student " + id + " has been deleted successfully!");
        } catch (StudentNotFoundException e) {
            redirectAttributes.addFlashAttribute("messages", e.getMessage());
        }
        return "redirect:/students";
    }

}
