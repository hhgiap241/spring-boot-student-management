package com.kms.mywebapp.card;

import org.springframework.data.repository.CrudRepository;

public interface StudentIdCardRepository extends CrudRepository<StudentIdCard, Integer> {
    StudentIdCard findStudentIdCardByStudentId(Integer id);
    /*
       select
        studentidc0_.id as id1_0_,
        studentidc0_.card_number as card_num2_0_,
        studentidc0_.student_id as student_3_0_
    from
        student_id_card studentidc0_
    left outer join
        students student1_
            on studentidc0_.student_id=student1_.id
    where
        student1_.email=?
     */
}
