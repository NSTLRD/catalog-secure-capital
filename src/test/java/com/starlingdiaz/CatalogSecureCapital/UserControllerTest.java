/**
 * @author Starling Diaz on 12/18/2023.
 * @Academy StarAcademy
 * @version CatalogSecureCapital 1.0
 * @since 12/18/2023.
 */

package com.starlingdiaz.CatalogSecureCapital;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starlingdiaz.CatalogSecureCapital.dto.UserDTO;
import com.starlingdiaz.CatalogSecureCapital.request.LoginRequest;
import com.starlingdiaz.CatalogSecureCapital.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    public void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest("javier@gmail.com", "123456");
        UserDTO mockUser = new UserDTO();
        mockUser.setEmail(loginRequest.getEmail());
        mockUser.setUsingMfa(false);

        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userService.getUserByEmail(loginRequest.getEmail())).thenReturn(mockUser);

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.user.email").value(loginRequest.getEmail()));
    }

    @Test
    public void testLoginFailureInvalidCredentials() throws Exception {
        LoginRequest loginRequest = new LoginRequest("javier@gmail.com", "wrongPassword");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

}
