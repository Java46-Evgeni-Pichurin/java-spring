package avgerage.service;

import java.time.LocalDateTime;

public interface AvgValuesService {
    int getAvgValue(long patientId, LocalDateTime from, LocalDateTime to);
    int getAvgValue(long patientId);
}
