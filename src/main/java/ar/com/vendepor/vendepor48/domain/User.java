package ar.com.vendepor.vendepor48.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id;

    /*
    @NotNull
    @NotBlank(message = "{username.notblank}")
    @Size(min = 6, max = 15)
    protected String username;
    */

    @NotNull
    @NotBlank
    @Size(min = 6, max = 50)
    @Email
    @Column(unique = true)
    protected String email;

    @JsonIgnore
    @NotNull
    @NotBlank(message = "{password.notblank}")
    @Size(max = 100)
    protected String password;

    @JsonProperty("created_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-03:00")
    @CreatedDate
    protected Date createdDate;

    @JsonProperty("last_modified_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-03:00")
    @LastModifiedDate
    protected Date lastModifiedDate;

    public User() { }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.createdDate = new Date();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}