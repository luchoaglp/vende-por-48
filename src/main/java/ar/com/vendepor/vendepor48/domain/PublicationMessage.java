package ar.com.vendepor.vendepor48.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PublicationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private LocalDateTime messageDateTime;

    private Boolean liked;

    @JsonIgnore
    @ManyToOne
    private Publication publication;

    @OneToOne(fetch = FetchType.EAGER)
    private Client client;

    public PublicationMessage(String message, LocalDateTime messageDateTime) {
        this.message = message;
        this.messageDateTime = messageDateTime;
        this.liked = false;
    }

    public PublicationMessage(String message, LocalDateTime messageDateTime, Boolean liked) {
        this.message = message;
        this.messageDateTime = messageDateTime;
        this.liked = liked;
    }

    @Override
    public String toString() {
        return "PublicationMessage{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", client=" + client +
                '}';
    }
}
