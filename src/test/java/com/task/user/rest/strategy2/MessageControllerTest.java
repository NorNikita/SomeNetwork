package com.task.user.rest.strategy2;

import com.task.user.config.jwt.JwtRequestFilter;
import com.task.user.dto.use.modelmapper.MessageDTO;
import com.task.user.rest.MessageController;
import com.task.user.servicies.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = { MessageController.class })
//public class MessageControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private MessageService messageService;
//
//    @MockBean
//    private JwtRequestFilter jwtRequestFilter;
//
//    @Before
//    public void init() {
//        when(messageService.getAllMessage()).thenReturn(getListMessages());
//    }
//
//    @Test
//    public void getAllMessages() throws Exception {
//        List<MessageDTO> allMessage = messageService.getAllMessage();
//
//        MvcResult result = mockMvc.perform(get("/api/allMessage").header("Authorization","eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b21faCIsImV4cCI6MTU3NjAxMzgwMCwiaWF0IjoxNTc1OTI3NDAwfQ.Ff6ZKv7w2YaGrEalLEuc8bG7blI-gt2PE3H11VpTHBxEdxfpauTaPJxr3p88ZMoEMjaF7p-WKI7dGxopBwPtag"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
////        assertEquals(MediaType.APPLICATION_JSON_UTF8_VALUE, mvcResult.getResponse().getContentType());
//    }
//
//    private List<MessageDTO> getListMessages() {
//        return new ArrayList<>(Arrays.asList(generateMessageDTO(), generateMessageDTO()));
//    }
//
//    private MessageDTO generateMessageDTO() {
//        return new MessageDTO(UUID.randomUUID().toString(), "testLogin");
//    }
//
//}
//
