package telran.monitoring.entities.jpa;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "doctors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Doctor {
    @Id
    String email;
    String name;
}
