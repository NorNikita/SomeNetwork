package com.task.user.rest.strategy1;

import com.task.user.dto.use.modelmapper.UserDTO;
import com.task.user.rest.UserController;
import com.task.user.servicies.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        when(userService.getUser(anyLong())).thenReturn(generateUserDTO());
    }

    @Test
    public void getUserById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/user/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(MediaType.APPLICATION_JSON_UTF8_VALUE, mvcResult.getResponse().getContentType());
    }

    private UserDTO generateUserDTO() {
        return new UserDTO("testLogin", "Test", "Login",
                null, null, null, null,
                null, null, null);
    }
}
