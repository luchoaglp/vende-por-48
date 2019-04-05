package ar.com.vendepor.vendepor48.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;
    private Double amount;
    private LocalDateTime startDate;

    @ManyToOne
    private Client client;

    public String shortDescription() {
        return description.substring(0, 90);
    }

}
