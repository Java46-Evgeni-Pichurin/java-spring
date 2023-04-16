package telran.monitoring.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
    @Email
    @NotEmpty
    public String email;
    @NotEmpty
    public String name;

    @Override
    public String toString() {
        return "DoctorDto [email=" + email + ", name=" + name + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DoctorDto other = (DoctorDto) obj;
        return Objects.equals(email, other.email) && Objects.equals(name, other.name);
    }
}