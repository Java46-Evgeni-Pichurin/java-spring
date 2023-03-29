package telran.monitoring.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class VisitDto {

    public long patientId;
    public String doctorEmail;
    public String date;

    @Override
    public String toString() {
        return "VisitDto [patientId=" + patientId + ", doctorEmail=" + doctorEmail + ", date=" + date + "]";
    }
}
