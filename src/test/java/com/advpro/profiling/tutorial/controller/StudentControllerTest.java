package com.advpro.profiling.tutorial.controller;

import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void highestGpa_found_OK() throws Exception {
        when(studentService.findStudentWithHighestGpa()).thenReturn(Optional.of(new Student()));

        mockMvc.perform(get("/highest-gpa"))
               .andExpectAll(
                   status().isOk()
               );
    }

    @Test
    void highestGpa_notFound_404() throws Exception {
        when(studentService.findStudentWithHighestGpa()).thenReturn(Optional.empty());

        mockMvc.perform(get("/highest-gpa"))
               .andExpectAll(
                   status().isNotFound()
               );
    }

    @Test
    void allStudentName_twoStudents() throws Exception {
        when(studentService.joinStudentNames()).thenReturn("John Doe, Jane Doe");

        mockMvc.perform(get("/all-student-name"))
            .andExpectAll(
                status().isOk(),
                content().string("John Doe, Jane Doe")
            );
    }
}
