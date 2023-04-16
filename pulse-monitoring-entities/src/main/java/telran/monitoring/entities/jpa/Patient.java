package telran.monitoring.entities.jpa;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "patients")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Patient {
    @Id
    long id;
    String name;
}
