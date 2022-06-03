package com.kms.mywebapp.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getBooks(){
        List<Book> bookList = (List<Book>) bookRepository.findAll();
        return bookList;
    }
    public boolean addBook(Book book){
        var id = bookRepository.findByName(book.getName());
        if(id != null)
            return false;
        bookRepository.save(book);
        return true;
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findAllAvailableBooks();
    }
    public Book getBook(Integer id){
        return bookRepository.findById(id).get();
    }

    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }
}
