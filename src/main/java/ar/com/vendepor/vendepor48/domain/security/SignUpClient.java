package ar.com.vendepor.vendepor48.domain.security;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class SignUpClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 7, max = 50)
    @Email
    private String email;

    @NotBlank
    @NotNull
    @NotBlank(message = "{password.notblank}")
    @Size(max = 100)
    private String password;

    @OneToOne
    private SignUpToken signUpToken;

    public SignUpClient() { }

    public SignUpClient(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignUpClient{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
