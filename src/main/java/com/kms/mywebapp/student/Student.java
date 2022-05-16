package com.kms.mywebapp.student;

import com.kms.mywebapp.card.StudentIdCard;

import javax.persistence.*;

@Entity(name = "Student")
@Table(
        name = "students",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "student_email_unique",
                        columnNames = "email"
                )
        }
)
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;
    @Column(nullable = false, name = "first_name", columnDefinition = "TEXT")
    private String firstName;
    @Column(nullable = false, name = "last_name", columnDefinition = "TEXT")
    private String lastName;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "student", fetch = FetchType.LAZY)
    private StudentIdCard studentIdCard;

    public StudentIdCard getStudentIdCard() {
        return studentIdCard;
    }

    public void setStudentIdCard(StudentIdCard studentIdCard) {
        this.studentIdCard = studentIdCard;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
