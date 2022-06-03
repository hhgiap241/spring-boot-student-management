package com.kms.mywebapp.course;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class CustomCourseRepositoryImpl implements CustomCourseRepository {
    @Autowired
    private EntityManager entityManager;
    @Override
    public List<Course> findCourseHaveMoreThan2StudentsLivingInHCMC() {
        Query query = entityManager.createNativeQuery("select * from course c where c.id in (\n" +
                "    select sc.course_id\n" +
                "    from student_course sc\n" +
                "    where sc.student_id in (\n" +
                "        select s.id\n" +
                "        from students s\n" +
                "        where s.country like '%HCM%'\n" +
                "    )\n" +
                "    group by sc.course_id\n" +
                "    having count(sc.course_id) > 2\n" +
                ");");
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
          courseList.add(course);
        });
        return courseList;
    }
}
