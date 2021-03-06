package ar.com.vendepor.vendepor48.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client extends User {

    @JsonProperty("first_name")
    @NotBlank(message = "{firstName.notblank}")
    @Size(min = 2, max = 50, message =  "{firstName.size}")
    protected String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "{lastName.notblank}")
    @Size(min = 2, max = 50, message =  "{lastName.size}")
    protected String lastName;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private List<Publication> publications = new ArrayList<>();

    public Client() {
        this.createdDate = LocalDateTime.now();
    }

    public Client addPublication(Publication publication) {
        publication.setClient(this);
        this.publications.add(publication);
        return this;
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
