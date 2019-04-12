package ar.com.vendepor.vendepor48.domain.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class SignUpToken {

    private static final long EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String token;

    private LocalDateTime expiryDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private SignUpClient signUpClient;

    @Transient
    private boolean tokenExpired;

    public SignUpToken(String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate();
    }

    private LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plusMinutes(EXPIRATION);
    }

    private void updateToken(String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate();
    }

    public boolean isTokenExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }

    public void setSignUpClient(SignUpClient signUpClient) {
        this.signUpClient = signUpClient;
        signUpClient.setSignUpToken(this);
    }

    @Override
    public String toString() {
        return "SignUpToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", expiryDate=" + expiryDate +
                ", tokenExpired=" + tokenExpired +
                '}';
    }
}
