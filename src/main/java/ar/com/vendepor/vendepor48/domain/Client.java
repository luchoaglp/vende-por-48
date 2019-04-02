package ar.com.vendepor.vendepor48.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "clients")
public class Client extends User {

    @JsonProperty("first_name")
    @NotBlank(message = "{firstName.notblank}")
    @Size(min = 2, max = 50, message =  "{firstName.size}")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "{lastName.notblank}")
    @Size(min = 2, max = 50, message =  "{lastName.size}")
    private String lastName;

    public Client() {
        this.createdDate = new Date();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
