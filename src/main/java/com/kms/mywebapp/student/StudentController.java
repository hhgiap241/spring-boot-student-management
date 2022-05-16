package com.kms.mywebapp.student;

import com.kms.mywebapp.card.StudentIdCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentService userService;


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
        return "user_form";
    }

    @PostMapping("/users/new/save")
    public String saveUser(Student user, RedirectAttributes redirectAttributes) {
        userService.save(user);
        redirectAttributes.addFlashAttribute("messages", "User has been saved successfully!");
        return "redirect:/users";
    }
    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Student student = userService.getUser(id);
            model.addAttribute("user", student);
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
            return "user_form";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("messages", e.getMessage());
            return "user_form";
        }
    }
    @DeleteMapping("/users/delete/{id}")
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
