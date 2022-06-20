package com.kms.mywebapp.book;

import com.kms.mywebapp.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping(value = "")
    public String getBooks(Model model) {
        List<Book> bookList = bookService.getBooks();
        model.addAttribute("bookList", bookList);
        return "books";
    }
    @GetMapping(value = "/add")
    public String addBook(Model model) {
        Book book = new Book();
        LocalDate today = LocalDate.now();
        String formattedDate = today. format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        book.setCreatedDate(formattedDate);
        model.addAttribute("book", book);
        model.addAttribute("pageTitle", "Add New Book");
        return "add_book_form";
    }
    @PostMapping(value = "/add")
    public String addBook(RedirectAttributes redirectAttributes, Book book) {
        var result = bookService.addBook(book);
        String message = result ? "Book added successfully" : "Book not added";
        redirectAttributes.addFlashAttribute("messages", message);
        return "redirect:/books";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(Model model, @PathVariable("id") Integer id) {
        try{
            Book book = bookService.getBook(id);
            model.addAttribute("pageTitle", "Edit Book");
            model.addAttribute("book", book);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "update_book_form";
    }
    @PostMapping("/edit/{id}")
    public String updateBook(RedirectAttributes redirectAttributes,
                             @ModelAttribute("book") Book book,
                             @RequestParam(value = "id") Integer id) {
        bookService.updateBook(book);
        redirectAttributes.addFlashAttribute("messages", "Book has been updated successfully!");
        return "redirect:/books";
    }
    @GetMapping("/delete/{id}")
    public String deleteBook(RedirectAttributes redirectAttributes, @PathVariable("id") Integer id) {
        bookService.deleteBook(id);
        redirectAttributes.addFlashAttribute("messages", "Book " + id + " has been deleted successfully!");
        return "redirect:/books";
    }
}
