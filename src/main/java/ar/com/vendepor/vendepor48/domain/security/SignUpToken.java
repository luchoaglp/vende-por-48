package ar.com.vendepor.vendepor48.domain.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class SignUpToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Date expiryDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private SignUpClient signUpClient;

    @Transient
    private boolean tokenExpired;

    public SignUpToken(String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate();
    }

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, EXPIRATION);
        return cal.getTime();
    }

    private void updateToken(String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate();
    }

    public boolean isTokenExpired() {
        return this.tokenExpired = this.expiryDate.getTime() - Calendar.getInstance().getTime().getTime() <= 0;
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
