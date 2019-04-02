package ar.com.vendepor.vendepor48.bootstrap;

import ar.com.vendepor.vendepor48.domain.Client;
import ar.com.vendepor.vendepor48.domain.security.SignUpClient;
import ar.com.vendepor.vendepor48.domain.security.SignUpToken;
import ar.com.vendepor.vendepor48.service.ClientService;
import ar.com.vendepor.vendepor48.service.security.SignUpTokenService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SignUpBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final SignUpTokenService signUpTokenService;
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;

    public SignUpBootstrap(SignUpTokenService signUpTokenService, ClientService clientService, PasswordEncoder passwordEncoder) {
        this.signUpTokenService = signUpTokenService;
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        String token = UUID.randomUUID().toString();

        SignUpClient signUpClient = new SignUpClient(
                "test@email.com",
                passwordEncoder.encode("123456"));

        SignUpToken signUpToken = new SignUpToken(token);
        signUpToken.setSignUpClient(signUpClient);

        signUpTokenService.save(signUpToken);

        SignUpToken signUpTokenSaved = signUpTokenService.findById(signUpToken.getId());

        Client client = new Client();

        client.setEmail(signUpTokenSaved.getSignUpClient().getEmail());
        client.setPassword(signUpTokenSaved.getSignUpClient().getPassword());
        client.setFirstName("fName");
        client.setLastName("lName");

        clientService.save(client);
    }
}
