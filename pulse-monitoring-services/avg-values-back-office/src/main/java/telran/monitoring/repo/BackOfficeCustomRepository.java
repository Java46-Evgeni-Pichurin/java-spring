package telran.monitoring.repo;

import java.time.LocalDateTime;

public interface BackOfficeCustomRepository {
    int getAvgValue(long patientId, LocalDateTime from, LocalDateTime to);
    int getAvgValue(long patientId);
}
