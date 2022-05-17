package com.kms.mywebapp.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping(value = "/books")
    public String getBooks(Model model) {
        List<Book> bookList = bookService.getBooks();
        model.addAttribute("bookList", bookList);
        return "books";
    }
    @GetMapping(value = "/books/add")
    public String addBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        model.addAttribute("pageTitle", "Add New Book");
        return "add_book_form";
    }
    @PostMapping(value = "/books/add")
    public String addBook(RedirectAttributes redirectAttributes, Book book) {
        var result = bookService.addBook(book);
        String message = result ? "Book added successfully" : "Book not added";
        redirectAttributes.addFlashAttribute("messages", message);
        return "redirect:/books";
    }
}
