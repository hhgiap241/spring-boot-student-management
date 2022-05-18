package com.kms.mywebapp.student;

import com.kms.mywebapp.book.Book;
import com.kms.mywebapp.book.BookService;
import com.kms.mywebapp.card.StudentIdCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("/users")
    public String showUserList(Model model){
        List<Student> userList = userService.listAll();
        model.addAttribute("listUsers", userList);
        return "users";
    }

    @GetMapping("/users/id-cards")
    public String showCardList(Model model){
        List<StudentIdCard> cardList = userService.listAllCard();
        model.addAttribute("listCards", cardList);
        return "cards";
    }

    @GetMapping("/users/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new Student());
        model.addAttribute("pageTitle", "Add New Student");
        return "add_user_form";
    }

    @PostMapping("/users/new/save")
    public String addNewUser(Student user, RedirectAttributes redirectAttributes) {
        boolean result = userService.addNewUser(user);
        String message;
        if(result)
            message = "User has been saved successfully!";
        else message = "User has not been saved!";
        redirectAttributes.addFlashAttribute("messages", message);
        return "redirect:/users";
    }
    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Student student = userService.getUser(id);
            List<Book> bookList = bookService.getBooks();
            model.addAttribute("user", student);
            model.addAttribute("bookList", bookList);
//            List<Book> books = new ArrayList<>();
//            model.addAttribute("bookValue", books);
            model.addAttribute("pageTitle", "Edit Student (ID: " + id + ")");
            return "update_user_form";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("messages", e.getMessage());
            return "update_user_form";
        }
    }
    @PostMapping("/users/edit/{id}")
    public String updateUser(RedirectAttributes redirectAttributes, @ModelAttribute("user") Student student, @RequestParam("id") Integer id) {
        userService.updateUser(id, student);
        redirectAttributes.addFlashAttribute("messages", "User has been updated successfully!");
        return "redirect:/users";
    }
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            userService.delete(id);
            redirectAttributes.addFlashAttribute("messages", "User " + id + " has been deleted successfully!");
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("messages", e.getMessage());
        }
        return "redirect:/users";
    }
}
