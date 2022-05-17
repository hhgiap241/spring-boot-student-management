package com.kms.mywebapp.card;

import com.kms.mywebapp.student.Student;

import javax.persistence.*;

@Entity(name="StudentIdCard")
@Table(name="student_id_card")
public class StudentIdCard {
    @Id
    @SequenceGenerator(
            name = "student_id_card_sequence",
            sequenceName = "student_id_card_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_id_card_sequence"
    )
    private Integer id;
    private String card_number;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id", referencedColumnName = "id")
    private Student student;

    public StudentIdCard() {
    }

    public StudentIdCard(String card_number) {
        this.card_number = card_number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
