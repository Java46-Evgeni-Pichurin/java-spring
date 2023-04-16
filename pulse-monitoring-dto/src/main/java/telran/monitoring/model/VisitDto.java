package telran.monitoring.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class VisitDto {
    @NotNull
    public long patientId;
    @Email
    public String doctorEmail;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    public String date;

    @Override
    public String toString() {
        return "VisitDto [patientId=" + patientId + ", doctorEmail=" + doctorEmail + ", date=" + date + "]";
    }
}
