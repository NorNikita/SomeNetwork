package com.task.user.rest.strategy3;

import com.task.user.Application;
import com.task.user.dto.use.modelmapper.MessageDTO;
import com.task.user.servicies.MessageService;
import com.task.user.servicies.UserDetailsServiceImpl;
import com.task.user.utils.JwtUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.lang.System.setProperty;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MessageControllerTest {

    static {
        setProperty("env", "dev");
    }

    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        when(messageService.getAllMessage()).thenReturn(getListMessages());
    }

    @Test
    public void getAllMessages() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/allMessage")
                .header("Authorization", getToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(MediaType.APPLICATION_JSON_UTF8_VALUE, result.getResponse().getContentType());
    }

    private String getToken() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        return jwtUtil.generateTokenForUser(userDetails);
    }

    private List<MessageDTO> getListMessages() {
        return new ArrayList<>(Arrays.asList(generateMessageDTO(), generateMessageDTO()));
    }

    private MessageDTO generateMessageDTO() {
        return new MessageDTO(UUID.randomUUID().toString(), "testLogin");
    }
}
