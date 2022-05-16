package com.kms.mywebapp.student;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface StudentRepository extends CrudRepository<Student, Integer> {
    public Long countById(Integer id);
    @Query(value = "select id from students where email = ?1", nativeQuery = true)
    public String findByEmail(String email);

}
