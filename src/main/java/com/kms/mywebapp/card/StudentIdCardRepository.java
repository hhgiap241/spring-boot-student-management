package com.kms.mywebapp.card;

import org.springframework.data.repository.CrudRepository;

public interface StudentIdCardRepository extends CrudRepository<StudentIdCard, Integer> {
    StudentIdCard findStudentIdCardByStudentId(Integer id);
}
