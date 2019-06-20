package ar.com.vendepor.vendepor48.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;
    private Double amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDateTime;

    @Transient
    private LocalDateTime endDateTime;

    private Boolean sold;

    @ManyToOne
    private Client client;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publication")
    private List<PublicationMessage> messages = new ArrayList<>();

    public Publication addPublicationMessage(PublicationMessage message) {
        message.setPublication(this);
        this.messages.add(message);
        return this;
    }

    public LocalDateTime getEndDateTime() {
        return startDateTime.plusHours(48);
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
