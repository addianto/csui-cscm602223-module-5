package com.advpro.profiling.tutorial.controller;

import com.advpro.profiling.tutorial.service.DataSeedService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DataSeedController.class)
class DataSeedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataSeedService dataSeedService;

    @Test
    void seedMaster_Ok() throws Exception {
        mockMvc.perform(get("/seed-data-master"))
               .andExpectAll(
                   status().isOk()
               );
    }

    @Test
    void seedStudents_Ok() throws Exception {
        mockMvc.perform(get("/seed-student-course"))
               .andExpectAll(
                   status().isOk()
               );
    }
}
