package com.kms.mywebapp;

import ch.qos.logback.core.net.AbstractSSLSocketAppender;
import com.kms.mywebapp.user.User;
import com.kms.mywebapp.user.UserRepository;
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
public class UserRepositoryTest {
    @Autowired private UserRepository userRepository;

    @Test
    public void testAddNew() {
        User user = new User();
        user.setEmail("sandra.murphy@example.com");
        user.setFirstName("Sandra");
        user.setLastName("Murphy");
        user.setPassword("sandra123456");
        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }
    @Test
    public void testListAll(){
        Iterable<User> users = userRepository.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);
        users.forEach(user -> {
            System.out.println(user.toString());
        });
    }

    @Test
    public void testUpdate(){
        User user = userRepository.findById(1).get();
        user.setPassword("sheila123456");
        userRepository.save(user);
        User updatedUser = userRepository.findById(1).get();
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo("sheila123456");
    }
    @Test
    public void testGetById() {
        Integer userId = 2;
        Optional<User> optionalUser = userRepository.findById(userId);
        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }
    @Test
    public void testDeleteById() {
        Integer userId = 2;
        userRepository.deleteById(userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        Assertions.assertThat(optionalUser).isNotPresent();
    }
}
