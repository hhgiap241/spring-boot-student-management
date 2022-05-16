package com.kms.mywebapp.student;

import com.kms.mywebapp.card.StudentIdCard;
import com.kms.mywebapp.card.StudentIdCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository userRepository;

    @Autowired
    private StudentIdCardRepository cardRepository;

    public List<Student> listAll(){
        return (List<Student>) userRepository.findAll();
    }

    public List<StudentIdCard> listAllCard(){
        return (List<StudentIdCard>) cardRepository.findAll();
    }

    public void save(Student user) {
        String id  =  userRepository.findByEmail(user.getEmail());
        StudentIdCard studentIdCard;
        if(id != null) {
            // exists
            studentIdCard = cardRepository.findStudentIdCardByStudentId(Integer.parseInt(id));
            studentIdCard.setCard_number(user.getFirstName().toLowerCase() + user.getLastName().toLowerCase() + "123");
        }else{
            studentIdCard = new StudentIdCard(user.getFirstName().toLowerCase() + user.getLastName().toLowerCase() + "123");
        }
        studentIdCard.setStudent(user);
        user.setStudentIdCard(studentIdCard);
        userRepository.save(user);
//        cardRepository.save(new StudentIdCard(
//                user.getFirstName().toLowerCase() + user.getLastName().toLowerCase() + "123",
//                user)
//        );
    }
    public Student getUser(Integer id) throws UserNotFoundException {
       Optional<Student> optionalUser =  userRepository.findById(id);
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