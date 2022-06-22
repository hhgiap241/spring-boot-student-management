package com.kms.mywebapp.student;

import com.kms.mywebapp.book.Book;
import com.kms.mywebapp.card.StudentIdCard;
import com.kms.mywebapp.card.StudentIdCardRepository;
import com.kms.mywebapp.student.exception.StudentExistsException;
import com.kms.mywebapp.student.exception.StudentNotFoundException;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class StudentService {
    private final StudentRepository userRepository;

    private final StudentIdCardRepository cardRepository;

    public StudentService(StudentRepository userRepository, StudentIdCardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    public List<Student> getAllStudents() {
        return (List<Student>) userRepository.findAll();
    }

    public List<StudentIdCard> getAllStudentIdCard() {
        return (List<StudentIdCard>) cardRepository.findAll();
    }

    public boolean addNewStudent(Student student) {
        String id = userRepository.findByEmail(student.getEmail());
        StudentIdCard studentIdCard = new StudentIdCard(student.getFirstName().toLowerCase() + student.getLastName().toLowerCase() + "123");
        if (id != null) {
            // exists
            throw new StudentExistsException("Student with email " + student.getEmail() + " already exists");
        }
        studentIdCard.setStudent(student);
        student.setStudentIdCard(studentIdCard);
        userRepository.save(student);
        return true;
//        cardRepository.save(new StudentIdCard(
//                user.getFirstName().toLowerCase() + user.getLastName().toLowerCase() + "123",
//                user)
//        );
    }

    public void updateStudentInfo(Integer id, Student student) {
        // get old email
        StudentIdCard studentIdCard;
        studentIdCard = cardRepository.findStudentIdCardByStudentId(id);
        studentIdCard.setCard_number(student.getFirstName().toLowerCase() + student.getLastName().toLowerCase() + "123");
        studentIdCard.setStudent(student);
        student.setStudentIdCard(studentIdCard);
        Set<Book> books = student.getBooks();
//        if(books != null) {
//            for(Book book: books) {
//                if(book.getStudent() == null) {
//                    book.setStudent(user);
//                }
//            }
//        }
        Optional.ofNullable(books).stream()
                .flatMap(Collection::stream)
                .filter(book -> book.getStudent() == null)
                .forEach(book -> book.setStudent(student));

        userRepository.save(student);
//        cardRepository.save(new StudentIdCard(
//                user.getFirstName().toLowerCase() + user.getLastName().toLowerCase() + "123",
//                user)
//        );
    }

    public Student getStudentById(Integer id){
        Optional<Student> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new StudentNotFoundException("Student not found for id " + id);
    }

    public void deleteStudent(Integer id){
        Long value = userRepository.countById(id);
        if (value == null || value == 0) {
            throw new StudentNotFoundException("Student with id " + id + " not found");
        }
        // select from 2 table then delete
        Student student = userRepository.findById(id).get();
//        for (Book book : student.getBooks()) {
//            book.setStudent(null);
//        }
        Optional.ofNullable(student.getBooks()).stream()
                .flatMap(books -> books.stream())
                .forEach(book -> book.setStudent(null));
//        student.getBooks().stream().forEach(System.out::println);
//        student.getBooks().stream().forEach(book -> book.setStudent(null));
        userRepository.deleteById(id);
    }

}
