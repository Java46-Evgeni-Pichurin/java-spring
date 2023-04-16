package telran.monitoring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import telran.monitoring.model.*;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class DbInitService {
    @Value("${spring.jpa.hibernate.ddl-auto: create}")
    String ddlAutoProp;
    @Value("${integration.test: true}")
    boolean isIntegrationTest;
    @Value("${integration.test.amount.patients: 10}")
    int nPatients;
    @Value("${integration.test.amount.visits: 50}")
    int nVisits;
    @Value("${integration.test.amount.doctors: 3}")
    int nDoctors;
    ThreadLocalRandom tlr = ThreadLocalRandom.current();
    VisitsService service;

    public DbInitService(VisitsService service) {
        this.service = service;
    }

    public void dbInit() {
        if (isIntegrationTest && ddlAutoProp.equals("create")) {
            initDb();
        }
    }

    private void initDb() {
        createPatients();
        createDoctors();
        createVisits();
    }

    private void createPatients() {
        IntStream.rangeClosed(1, nPatients).forEach(i -> service.addPatient(new PatientDto(i, "patient" + i)));
    }

    private void createDoctors() {
        IntStream.rangeClosed(1, nDoctors).forEach(i -> service.addDoctor
                (new DoctorDto(String.format("doctor%d@gmail.com", i), String.format("Doctor%d", i))));
    }

    private void createVisits() {
        Stream.generate(this::getRandomVisit).limit(nVisits).forEach(v -> service.addVisit(v));
    }

    private VisitDto getRandomVisit() {
        long patientId = tlr.nextLong(1, nPatients + 1);
        String doctorEmail = String.format("doctor%d@gmail.com", tlr.nextInt(1, nDoctors + 1));
        String date = LocalDate.of(2023, tlr.nextInt(1, 4), tlr.nextInt(1, 28)).toString();
        return new VisitDto(patientId, doctorEmail, date);
    }
}
