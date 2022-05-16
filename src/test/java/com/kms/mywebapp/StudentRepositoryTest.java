package com.kms.mywebapp;

import com.kms.mywebapp.student.Student;
import com.kms.mywebapp.student.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class StudentRepositoryTest {
    @Autowired private StudentRepository userRepository;

    @Test
    public void testAddNew() {
        Student user = new Student();
        user.setEmail("sandra.murphy@example.com");
        user.setFirstName("Sandra");
        user.setLastName("Murphy");
        Student savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }
    @Test
    public void testListAll(){
        Iterable<Student> users = userRepository.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);
        users.forEach(user -> {
            System.out.println(user.toString());
        });
    }

    @Test
    public void testUpdate(){
        Student user = userRepository.findById(1).get();
        user.setFirstName("Nana");
        userRepository.save(user);
        Student updatedUser = userRepository.findById(1).get();
        Assertions.assertThat(updatedUser.getFirstName()).isEqualTo("Nana");
    }
    @Test
    public void testGetById() {
        Integer userId = 2;
        Optional<Student> optionalUser = userRepository.findById(userId);
        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }
    @Test
    public void testDeleteById() {
        Integer userId = 2;
        userRepository.deleteById(userId);
        Optional<Student> optionalUser = userRepository.findById(userId);
        Assertions.assertThat(optionalUser).isNotPresent();
    }
}
