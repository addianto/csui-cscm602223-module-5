package com.advpro.profiling.tutorial.service;

import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.repository.StudentCourseRepository;
import com.advpro.profiling.tutorial.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StudentServiceTest {

    private static final List<Student> TWO_STUDENTS = List.of(
        new Student("A", "John Doe", "Faculty of Test", 1.23),
        new Student("B", "Jane Doe", "Faculty of Test", 3.45)
    );

    private AutoCloseable closeable;

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentCourseRepository studentCourseRepository;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void findStudentWithHighestGpa_twoStudents_returnCorrectStudent() {
        when(studentRepository.findAll()).thenReturn(TWO_STUDENTS);

        Optional<Student> foundStudent = studentService.findStudentWithHighestGpa();

        assertTrue(foundStudent.isPresent());
        assertEquals(3.45, foundStudent.get().getGpa());

        // Try again with a reversed list of students
        when(studentRepository.findAll()).thenReturn(TWO_STUDENTS.reversed());

        foundStudent = studentService.findStudentWithHighestGpa();

        assertTrue(foundStudent.isPresent());
        assertEquals(3.45, foundStudent.get().getGpa());
    }

    @Test
    void joinStudentNames_oneStudent_returnCorrectName() {
        when(studentRepository.findAll()).thenReturn(TWO_STUDENTS.subList(0, 1));

        String output = studentService.joinStudentNames();

        assertEquals("John Doe", output);
    }

    @Test
    void joinStudentNames_twoStudents_returnCorrectName() {
        when(studentRepository.findAll()).thenReturn(TWO_STUDENTS);

        String output = studentService.joinStudentNames();

        assertEquals("John Doe, Jane Doe", output);
    }
}
