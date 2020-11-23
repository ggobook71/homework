package com.kakao.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.homework.data.sprinkling.dto.SprinklingBodyDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SprinklingApi.class)
@ExtendWith(MockitoExtension.class)
class SprinklingApiTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    SprinklingApi sprinklingApi;

    /*
    Controller Request Value type test
    뿌리기 rest-api request-response 단위테스트
    통합테스트 환경은 postman으로 진행
     */
    @Test
    void requestSprinklingApi() throws Exception {
        SprinklingBodyDto sprinklingBodyDto = new SprinklingBodyDto();
        sprinklingBodyDto.setDistMoney(5000);
        sprinklingBodyDto.setReceiveNum(5);
        mockMvc.perform(post("/api/sprinkling").
                header("X-USER-ID", 244).
                header("X-ROOM-ID", "A33").
                accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sprinklingBodyDto))
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
    }

    /*
    Controller Request Value type test
    받기 rest-api request-response 단위테스트
     */
    @Test
    void requestReceivingApi() throws Exception {
        Map<String, String> map = new HashMap<>();
        mockMvc.perform(get("/api/receiving/a75").
                header("X-USER-ID", 245).
                header("X-ROOM-ID", "A33").
                accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
    }

    /*
    Controller Request Value type test
    조회하기 rest-api request-response 단위테스트
     */
    @Test
    void requestSearchingApi() throws Exception {
        Map<String, String> map = new HashMap<>();
        mockMvc.perform(get("/api/searching/a33").
                header("X-USER-ID", 244).
                header("X-ROOM-ID", "A33").
                accept(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk());
    }
}