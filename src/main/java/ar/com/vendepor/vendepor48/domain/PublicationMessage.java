package ar.com.vendepor.vendepor48.domain;

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

    private LocalDateTime dateTime;

    @ManyToOne
    private Publication publication;

    public PublicationMessage(String message, LocalDateTime dateTime) {
        this.message = message;
        this.dateTime = dateTime;
    }
}
