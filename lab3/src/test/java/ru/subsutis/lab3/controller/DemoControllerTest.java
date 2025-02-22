package ru.subsutis.lab3.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DemoController.class)
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAuthorPage_ReturnsAuthorViewWithAttributes() throws Exception {
        mockMvc.perform(get("/demo/author"))
                .andExpect(status().isOk())
                .andExpect(view().name("author"))
                .andExpect(model().attribute("header", "Информация об авторе: "))
                .andExpect(model().attribute("name", "Виталий Медведенко"));
    }
}
