package ar.com.vendepor.vendepor48.domain.security;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

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

    public SignUpToken() {}

    public SignUpToken(String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate();
    }

    /*
    public SignUpToken(String token, SignUpClient signUpClient) {
        this.token = token;
        this.signUpClient = signUpClient;
        this.expiryDate = calculateExpiryDate();
    }
    */

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public SignUpClient getSignUpClient() {
        return signUpClient;
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
