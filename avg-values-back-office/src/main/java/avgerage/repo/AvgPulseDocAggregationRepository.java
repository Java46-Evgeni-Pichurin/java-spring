package avgerage.repo;

import java.time.LocalDateTime;

public interface AvgPulseDocAggregationRepository {
    int getAvgValue(long patientId, LocalDateTime from, LocalDateTime to);
    int getAvgValue(long patientId);
}
