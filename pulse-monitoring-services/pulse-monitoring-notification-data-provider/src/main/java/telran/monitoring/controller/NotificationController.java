package telran.monitoring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import telran.monitoring.model.NotificationData;
import telran.monitoring.service.DataProviderService;


@RestController
@RequestMapping("notification")
public class NotificationController {
    DataProviderService dataProviderService;

    public NotificationController(DataProviderService dataProviderService) {
        this.dataProviderService = dataProviderService;
    }

    @GetMapping("visits/last")
    NotificationData getMarksByName(@RequestParam(name = "patientId") Long patientId) {
        return dataProviderService.getNotificationData(patientId);
    }
}
