package com.kms.mywebapp.student;

import com.kms.mywebapp.book.Book;
import com.kms.mywebapp.card.StudentIdCard;
import com.kms.mywebapp.course.Course;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Student")
@Table(
        name = "students"
//        ,uniqueConstraints = {
//                @UniqueConstraint(
//                        name = "student_email_unique",
//                        columnNames = "email"
//                )
//        }
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

    public Student(){}

    public Student(String email, String firstName, String lastName,
                   String country, StudentIdCard studentIdCard,
                   Set<Book> books, Set<Course> courses) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.studentIdCard = studentIdCard;
        this.books = books;
        this.courses = courses;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(nullable = false, name = "country", columnDefinition = "TEXT")
    private String country;
    //cascade = CascadeType.ALL, orphanRemoval = true
    @OneToOne(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private StudentIdCard studentIdCard;

    @OneToMany(mappedBy = "student")
    private Set<Book> books;

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id")
    )
    private Set<Course> courses;

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
    public void addBook(Book book) {
        this.books.add(book);
        book.setStudent(this);
    }
    public void removeBook(Book book) {
        this.books.remove(book);
        book.setStudent(null);
    }
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
    public void addCourse(Course course){
        this.courses.add(course);
    }
    public void removeCourse(Course course){
        this.courses.remove(course);
    }
}
