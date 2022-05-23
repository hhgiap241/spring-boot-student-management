package com.kms.mywebapp.book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    public Book findByName(String name);
    @Query(value = "select * from books b where b.student_id is NULL", nativeQuery = true)
    public List<Book> findAllAvailableBooks();
}
