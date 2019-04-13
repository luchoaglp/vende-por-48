package ar.com.vendepor.vendepor48.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;
    private Double amount;
    private LocalDateTime startDateTime;

    @Transient
    private LocalDateTime endDateTime;

    @ManyToOne
    private Client client;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publication")
    @OrderBy("id DESC")
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
