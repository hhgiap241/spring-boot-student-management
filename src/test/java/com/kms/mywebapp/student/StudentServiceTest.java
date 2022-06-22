package com.kms.mywebapp.student;

import com.kms.mywebapp.card.StudentIdCard;
import com.kms.mywebapp.card.StudentIdCardRepository;
import com.kms.mywebapp.student.exception.StudentExistsException;
import com.kms.mywebapp.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


/**
 * @author : giaphoang
 * @mailto : hoanghuugiap241@gmail.com
 * @created : 6/21/2022, Tuesday
 * @project: MyWebApp
 **/

class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentIdCardRepository studentIdCardRepository;
    private AutoCloseable autoCloseable;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new StudentService(studentRepository, studentIdCardRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllStudents() {
        // when
        underTest.getAllStudents();
        // then
        verify(studentRepository).findAll();
        /*
            this is strange because we already tested the method findAll() in student repository
            but we don't need to test the student repo because we only want to test the student service
            ==> make it more faster
        */
    }

    @Test
    void getAllStudentIdCard() {
        // when
        underTest.getAllStudentIdCard();
        // then
        verify(studentIdCardRepository).findAll();
    }

    @Test
    void addNewStudent() {
        // given
        Student student = new Student("casemiro.valdez@gmail.com",
                "Casemiro", "Valdez", "Venezuela",
                null, null, null);
        // when
        underTest.addNewStudent(student);
        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        // we want to verify that the student repository save method is called with the student we passed in
        verify(studentRepository).save(studentArgumentCaptor.capture());
        // this capturedStudent is what the studentService received
        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(student);
    }
    @Test
    void willNotAddNewStudentIfEmailAlreadyExists() {
        // given
        Student student = new Student("casemiro.valdez@gmail.com",
                "Casemiro", "Valdez", "Venezuela",
                null, null, null);

        given(studentRepository.findByEmail(anyString())).willReturn("8");
        // when
        // then
        assertThatThrownBy(() -> underTest.addNewStudent(student))
                .isInstanceOf(StudentExistsException.class)
                .hasMessageContaining("Student with email " + student.getEmail() + " already exists");
        verify(studentRepository, never()).save(any());
    }

    @Test
    void updateStudentInfo() {
        // given
        Integer id = 1;
        Student student = new Student("casemiro.valdez@gmail.com",
                "Casemiro", "Valdez", "Venezuela",
                null, null, null);
        given(studentIdCardRepository.findStudentIdCardByStudentId(id)).willReturn(new StudentIdCard());
        // then
        underTest.updateStudentInfo(id, student);
        // when
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();
        verify(studentRepository).save(student);
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void getStudentById() {
        // given
        Integer id = 1;
        given(studentRepository.findById(id)).willReturn(Optional.of(new Student("casemiro.valdez@gmail.com",
                "Casemiro", "Valdez", "Venezuela",
                null, null, null)));
        // when
        underTest.getStudentById(id);
        // then
        verify(studentRepository).findById(id);
    }
    @Test
    void willThrowWhenStudentIdNotFound(){
        // given
        Integer id = 1;
        given(studentRepository.findById(id)).willReturn(Optional.empty());
        // when
        // then
        assertThatThrownBy(() -> underTest.getStudentById(id))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student not found for id " + id);
    }
    @Test
    void deleteStudent() {
        // given
        Integer id = 1;
        given(studentRepository.countById(id)).willReturn(Long.valueOf(1));
        given(studentRepository.findById(id))
                .willReturn(Optional.of(new Student("casemiro.valdez@gmail.com",
                        "Casemiro", "Valdez", "Venezuela",
                        null, null, null)));
        // when
        underTest.deleteStudent(id);
        // then
        verify(studentRepository).deleteById(id);
    }
    @Test
    void willThrowWhenDeleteStudentWithInvalidId() {
        // given
        Integer id = 1;
        given(studentRepository.countById(id)).willReturn(Long.valueOf(0));
        // when
        // then
        assertThatThrownBy(() -> underTest.deleteStudent(id))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + id + " not found");
    }
}