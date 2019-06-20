package ar.com.vendepor.vendepor48.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class PublicationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private LocalDateTime messageDateTime;

    private Boolean liked;

    private Boolean sold;

    @JsonIgnore
    @ManyToOne
    private Publication publication;

    @OneToOne(fetch = FetchType.EAGER)
    private Client client;

    public PublicationMessage() { }

    public PublicationMessage(String description, LocalDateTime messageDateTime) {
        this.description = description;
        this.messageDateTime = messageDateTime;
        this.liked = false;
        this.sold = false;
    }

    public PublicationMessage(String description, LocalDateTime messageDateTime, Boolean liked) {
        this.description = description;
        this.messageDateTime = messageDateTime;
        this.liked = liked;
        this.sold = false;
    }

    @Override
    public String toString() {
        return "PublicationMessage{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", client=" + client +
                '}';
    }
}
