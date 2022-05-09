package com.kms.mywebapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;

    public List<User> listAll(){
        return (List<User>) userRepository.findAll();
    }

    public void save(User user) {
        userRepository.save(user);
    }
    public User getUser(Integer id) throws UserNotFoundException {
       Optional<User> optionalUser =  userRepository.findById(id);
       if(optionalUser.isPresent()) {
           return optionalUser.get();
       }
       throw new UserNotFoundException("User not found for id " + id);
    }
    public void delete(Integer id) throws UserNotFoundException {
        Long value = userRepository.countById(id);
        if(value == null || value == 0) {
            throw new UserNotFoundException("User not found for id " + id);
        }
        userRepository.deleteById(id);
    }
}
