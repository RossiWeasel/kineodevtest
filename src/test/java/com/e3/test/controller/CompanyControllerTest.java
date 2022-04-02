package com.e3.test.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldSuccessWhenGetCompany() throws Exception {
        this.mockMvc.perform(get("/company/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldSuccessWhenSearchEmployee() throws Exception {
        this.mockMvc.perform(get("/employee/search/company/K")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Annie")));
    }
}
