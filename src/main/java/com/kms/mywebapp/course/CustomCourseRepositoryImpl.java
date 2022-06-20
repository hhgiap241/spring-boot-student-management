package com.kms.mywebapp.course;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomCourseRepositoryImpl implements CustomCourseRepository {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Course> findCourseHaveMoreThan2StudentsLivingInHCMC() {
        Query query = entityManager.createNativeQuery("select *\n" +
                "from course c\n" +
                "inner join (\n" +
                "    select course_id, student_id\n" +
                "    from student_course sc\n" +
                "    where sc.course_id in (select sc.course_id\n" +
                "                           from student_course sc\n" +
                "                           group by sc.course_id\n" +
                "                           having count(*) > 2)) rs\n" +
                "    on c.id = rs.course_id;");
        List<Object[]> resultList = query.getResultList();
        entityManager.close();

        // select * from course c where c.id in (
        //    select sc.course_id
        //    from student_course sc
        //    where sc.student_id in (
        //        select s.id
        //        from students s
        //        where s.country like '%HCM%'
        //    )
        //    group by sc.course_id
        //    having count(sc.course_id) > 2
        //);
        ArrayList<Course> courseList = new ArrayList();
        resultList.forEach(object -> {
            Course course = new Course();
            course.setId((Integer) object[0]);
            course.setTitle((String) object[1]);
            course.setDescription((String) object[2]);
//            course.setStudents();
            courseList.add(course);
        });
        // some problem here
        return courseList;
    }
}
