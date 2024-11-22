package user.whrjs.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("메인 페이지 접속 확인")
    void home() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

}