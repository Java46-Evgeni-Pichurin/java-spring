package telran.monitoring.service;

import telran.monitoring.model.NotificationData;
import telran.monitoring.model.PulseJump;

public interface NotificationDataProvider {
    NotificationData getData(long patientId);

    void jumpProcessing(PulseJump jump);
}
