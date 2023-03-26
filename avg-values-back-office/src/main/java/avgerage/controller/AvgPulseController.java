package avgerage.controller;


import org.springframework.web.bind.annotation.*;
import avgerage.service.AvgValuesService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("avgPulse")
public class AvgPulseController {
    AvgValuesService service;

    public AvgPulseController(AvgValuesService service) {
        this.service = service;
    }

    @GetMapping
    int getAvgPulse(@RequestParam(name = "patientId") int patientId,
                    @RequestParam(name = "dateFrom", defaultValue = "", required = false) LocalDateTime dateFrom,
                    @RequestParam(name = "dateTo", defaultValue = "", required = false) LocalDateTime dateTo
    ) {
        return dateFrom == null || dateTo == null ?
                service.getAvgValue(patientId) :
                service.getAvgValue(patientId, dateFrom, dateTo);
    }

}
