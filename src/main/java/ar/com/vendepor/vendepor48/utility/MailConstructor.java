package ar.com.vendepor.vendepor48.utility;

import ar.com.vendepor.vendepor48.domain.security.SignUpClient;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MailConstructor {

    private final Environment env;

    public MailConstructor(Environment env) {
        this.env = env;
    }


    public SimpleMailMessage constructSignUpTokenEmail(String contextPath,
                                                       Locale locale,
                                                       String token,
                                                       SignUpClient signUpClient) {

        String url = contextPath + "/register/" + token;
        //String message = "\nPlease click on this link to verify your email and edit your personal information. ";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(signUpClient.getEmail());
        email.setSubject("Vende por 48 - New Client");
        email.setText(url);
        email.setFrom(env.getProperty("support.email"));

        return email;
    }
}
