package com.kms.mywebapp.course;

import com.kms.mywebapp.student.Student;

import javax.persistence.*;
import java.util.Set;

@Entity(name="Course")
@Table(name="course", uniqueConstraints={
        @UniqueConstraint(columnNames="title")
})
public class Course {
    @Id
    @SequenceGenerator(name = "course_sequence", sequenceName = "course_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_sequence")
    private Integer id;
    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
