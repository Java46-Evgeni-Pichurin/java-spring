package telran.monitoring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import telran.monitoring.model.NotificationData;
import telran.monitoring.service.DataProviderService;


@RestController
@RequestMapping("notification")
public class NotificationController {
    static Logger LOG = LoggerFactory.getLogger(NotificationController.class);
    DataProviderService dataProviderService;

    public NotificationController(DataProviderService dataProviderService) {
        this.dataProviderService = dataProviderService;
    }

    @GetMapping("visits/last")
    NotificationData getNotificationDataByPatientId(@RequestParam(name = "patientId") Long patientId) {
        LOG.debug("received request for getting notification data by patient with id: {}", patientId);
        return dataProviderService.getNotificationData(patientId);
    }
}
