package ar.com.vendepor.vendepor48.controller;

import ar.com.vendepor.vendepor48.domain.Client;
import ar.com.vendepor.vendepor48.domain.security.RegisterClient;
import ar.com.vendepor.vendepor48.domain.security.SignUpClient;
import ar.com.vendepor.vendepor48.domain.security.SignUpToken;
import ar.com.vendepor.vendepor48.exception.SignUpTokenException;
import ar.com.vendepor.vendepor48.service.ClientService;
import ar.com.vendepor.vendepor48.service.security.SignUpTokenService;
import ar.com.vendepor.vendepor48.utility.MailConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@Controller
public class ClientController {

    private final ClientService clientService;
    private final MailConstructor mailConstructor;
    private final PasswordEncoder passwordEncoder;
    private final SignUpTokenService signUpTokenService;

    public ClientController(ClientService clientService, MailConstructor mailConstructor, PasswordEncoder passwordEncoder, SignUpTokenService signUpTokenService) {
        this.clientService = clientService;
        this.mailConstructor = mailConstructor;
        this.passwordEncoder = passwordEncoder;
        this.signUpTokenService = signUpTokenService;
    }

    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }

    @GetMapping("/signup")
    public String signup(Model model,
                         Principal principal) {

        // If user is in session
        if(principal != null) {
            return "redirect:/home";
        }

        model.addAttribute("signUpClient", new SignUpClient());

        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid SignUpClient signUpClient,
                         BindingResult result,
                         HttpServletRequest request,
                         Model model /* Dev */) {

        if(result.hasErrors()) {
            return "signup";
        }

        String token = UUID.randomUUID().toString();

        signUpClient.setEmail(signUpClient.getEmail().trim());
        signUpClient.setPassword(passwordEncoder.encode(signUpClient.getPassword()));

        SignUpToken signUpToken = new SignUpToken(token);
        signUpToken.setSignUpClient(signUpClient);

        signUpTokenService.save(signUpToken);

        String appUrl = "http://" + request.getServerName() +
                    ":" + request.getServerPort() + request.getContextPath();

        SimpleMailMessage smm = mailConstructor.constructSignUpTokenEmail(appUrl,
                request.getLocale(),
                token, signUpClient);

        model.addAttribute("signUpToken", signUpToken); // Dev
        model.addAttribute("smm", smm); // Dev

        return "signup"; // Dev
    }

    @GetMapping("/register/{token}")
    public String register(@PathVariable("token") String token,
                           Model model,
                           Principal principal) {

        // If user is in session
        if(principal != null) {
            return "redirect:/home";
        }

        SignUpToken signUpToken = signUpTokenService.findByToken(token);

        if(signUpToken == null) {
            throw new SignUpTokenException("Token inválido");
        }

        if(signUpToken.isTokenExpired()) {
            throw new SignUpTokenException("El token ha expirado");
        }

        model.addAttribute("signUpToken", signUpToken);
        model.addAttribute("registerClient", new RegisterClient());

        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("tokenId") Long tokenId,
                           @ModelAttribute("token") String token,
                           @Valid RegisterClient registerClient,
                           BindingResult result,
                           HttpServletRequest request) {

        if(result.hasErrors()) {
            return "signup";
        }

        SignUpToken signUpToken = signUpTokenService.findById(tokenId);

        if(signUpToken == null) {
            throw new SignUpTokenException("Token inválido");
        }

        if(!signUpToken.getToken().equals(token)) {
            throw new SignUpTokenException("Token inválido");
        }
        if(signUpToken.isTokenExpired()) {
            throw new SignUpTokenException("El token ha expirado");
        }

        Client client = new Client();
        client.setEmail(signUpToken.getSignUpClient().getEmail());
        client.setPassword(signUpToken.getSignUpClient().getPassword());
        client.setFirstName(registerClient.getFirstName().trim());
        client.setLastName(registerClient.getLastName().trim());

        clientService.save(client);

        return "signin";
    }

}
