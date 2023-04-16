package telran.monitoring.controller;

import org.springframework.web.bind.annotation.*;

import telran.monitoring.model.NotificationData;
import telran.monitoring.service.DataProviderService;

@RestController
@RequestMapping("data")
public class NotificationDataProviderController {
    DataProviderService dataProvider;

    public NotificationDataProviderController(DataProviderService dataProvider) {
        this.dataProvider = dataProvider;
    }

    @GetMapping("/{patientId}")
    NotificationData getNotificationData(@PathVariable long patientId) {
        return dataProvider.getNotificationData(patientId);
    }
}
