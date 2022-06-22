package com.kms.mywebapp.student;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;


/**
 * @author : giaphoang
 * @mailto : hoanghuugiap241@gmail.com
 * @created : 6/21/2022, Tuesday
 * @project: MyWebApp
 **/
@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @Test
    void countById() {
        // give
        Student student = new Student("casemiro.valdez@gmail.com",
                "Casemiro", "Valdez", "Venezuela",
                null, null, null);

        underTest.save(student);
        // when
        Long count = underTest.countById(student.getId());
        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    void findByEmail() {
        // give
        String email = "casemiro.valdez@gmail.com";
        Student student = new Student("casemiro.valdez@gmail.com",
                "Casemiro", "Valdez", "Venezuela",
                null, null, null);

        underTest.save(student);
        // when
        underTest.findByEmail(email);
        // then
        assertThat(student.getEmail()).isEqualTo(email);
    }
//    @Test
//    void assumptionThat() {
//        String someString = "Just a string";
//        assumingThat(
//                someString.equals("Just a string"),
//                () -> assertEquals(2 + 2, 4)
//        );
//    }
//    @Test
//    void falseAssumption() {
//        assumeFalse(5 < 1);
//        assertEquals(5 + 2, 7);
//    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }
}