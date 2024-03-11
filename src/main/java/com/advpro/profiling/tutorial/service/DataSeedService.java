package com.advpro.profiling.tutorial.service;

import com.advpro.profiling.tutorial.model.Course;
import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.model.StudentCourse;
import com.advpro.profiling.tutorial.repository.CourseRepository;
import com.advpro.profiling.tutorial.repository.StudentCourseRepository;
import com.advpro.profiling.tutorial.repository.StudentRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;

/**
 * @author muhammad.khadafi
 */
@Service
public class DataSeedService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Locale INDONESIAN = new Locale.Builder().setLanguage("id").setRegion("ID").build();

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;

    @Value("${seed.students:20000}")
    private int numOfStudents;

    @Value("${seed.courses:10}")
    private int numOfCourses;

    @Autowired
    public DataSeedService(StudentRepository studentRepository, CourseRepository courseRepository, StudentCourseRepository studentCourseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentCourseRepository = studentCourseRepository;
    }

    public void seedStudent() {
        Faker faker = new Faker(INDONESIAN);

        for (int i = 0; i < numOfStudents; i++) {
            Student student = new Student();
            student.setStudentCode(faker.code()
                                        .ean8());
            student.setName(faker.name()
                                 .fullName());
            student.setFaculty(faker.educator()
                                    .course());
            student.setGpa(faker.number()
                                .randomDouble(2, 2, 4));

            studentRepository.save(student);
        }
    }

    public void seedCourse() {
        Faker faker = new Faker(INDONESIAN);
        for (int i = 0; i < numOfCourses; i++) {
            Course course = new Course();
            course.setCourseCode(faker.code()
                                      .ean8());
            course.setName(faker.book()
                                .title());
            course.setDescription(faker.lorem()
                                       .sentence());

            courseRepository.save(course);
        }
    }

    public void seedStudentCourses() {
        List<Student> students = studentRepository.findAll();
        List<Course> courses = courseRepository.findAll();

        for (Student student : students) {
            List<Course> selectedCourses = RANDOM.ints(0, courses.size())
                                                 .distinct()
                                                 .limit(2)
                                                 .mapToObj(courses::get)
                                                 .toList();

            for (Course course : selectedCourses) {
                StudentCourse studentCourse = new StudentCourse(student, course);
                studentCourseRepository.save(studentCourse);
            }
        }

    }

}
