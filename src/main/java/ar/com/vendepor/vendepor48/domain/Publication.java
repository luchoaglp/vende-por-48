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
