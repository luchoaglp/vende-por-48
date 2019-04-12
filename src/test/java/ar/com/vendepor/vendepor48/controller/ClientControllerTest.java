package ar.com.vendepor.vendepor48.controller;

import ar.com.vendepor.vendepor48.StandaloneMvcTestViewResolver;
import ar.com.vendepor.vendepor48.domain.security.SignUpToken;
import ar.com.vendepor.vendepor48.service.ClientService;
import ar.com.vendepor.vendepor48.service.security.SignUpTokenService;
import ar.com.vendepor.vendepor48.utility.MailConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    ClientService clientService;

    @Mock
    MailConstructor mailConstructor;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    SignUpTokenService signUpTokenService;

    @InjectMocks
    ClientController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setViewResolvers(new StandaloneMvcTestViewResolver())
                .build();
    }

    @Test
    void signin() throws Exception {
        mockMvc.perform(get("/signin"))
                .andExpect(status().isOk())
                .andExpect(view().name("signin"));
    }

    @Test
    void getSignup() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("signUpClient"));
    }

    @Test
    void postSignup() throws Exception {

        SignUpToken signUpToken = new SignUpToken("token");

        when(signUpTokenService.save(any())).thenReturn(signUpToken);

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "email@email.com")
                .param("password", "123456"))
                .andExpect(status().isOk())
                //.andExpect(status().is3xxRedirection())
                .andExpect(view().name("signup"));
    }

    @Test
    void getRegister() throws Exception {

        SignUpToken signUpToken = new SignUpToken("token");

        when(signUpTokenService.findByToken(anyString())).thenReturn(signUpToken);

        mockMvc.perform(get("/register/token"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("signUpToken", "registerClient"));
    }

    @Test
    void postRegister() throws Exception {

    }
}