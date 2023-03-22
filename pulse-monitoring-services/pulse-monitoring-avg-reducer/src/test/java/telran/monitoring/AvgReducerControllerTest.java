package telran.monitoring;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.monitoring.entities.ProbesList;
import telran.monitoring.model.*;
import telran.monitoring.repo.ProbeListRepository;
import telran.monitoring.service.AvgReducerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest(classes = AvgReducerAppl.class)
@Import(TestChannelBinderConfiguration.class)
//loading to Application Context producer/consumer for Spring Cloud Messaging
public class AvgReducerControllerTest {
    private static final long PATIENT_ID_NO_AVG = 123;
    private static final long PATIENT_ID_AVG = 124;
    private static final long PATIENT_NO_REDIS_DATA = 125;
    private static final int VALUE = 100;

    @MockBean
    AvgReducerService service;
    @MockBean
    ProbeListRepository probesListRepository;
    @Autowired
    AvgReducerService realService;
    @Autowired
    InputDestination producer;
    @Autowired
    OutputDestination consumer;
    PulseProbe probeNoAvg = new PulseProbe(PATIENT_ID_NO_AVG, 0, 0, VALUE);
    PulseProbe probeAvgExpected = new PulseProbe(PATIENT_ID_AVG, 0, 0, VALUE);
    PulseProbe probeNoData = new PulseProbe(PATIENT_NO_REDIS_DATA, 0, 0, VALUE);
    ProbesList probesNoList = new ProbesList(PATIENT_ID_NO_AVG, new ArrayList<>());
    ProbesList probesList = new ProbesList(PATIENT_ID_AVG, List.of(10, 10, 10, 10, 10, 10, 10, 10, 10, 10));


    String bindingNameProducer = "pulseProbeConsumer-in-0";
    @Value("${app.avg.binding.name}")
    String bindingNameConsumer;

    @BeforeEach
    void mockingService() {
        //No REDIS data
        when(probesListRepository.findById(PATIENT_NO_REDIS_DATA)).thenReturn(Optional.empty());
        //There is REDIS data but no jump
        when(probesListRepository.findById(PATIENT_ID_NO_AVG)).thenReturn(Optional.of(probesNoList));
        //There is REDIS data with a jump
        when(probesListRepository.findById(PATIENT_ID_AVG)).thenReturn(Optional.of(probesList));
        when(service.reduce(probeAvgExpected)).thenReturn(VALUE);
        when(service.reduce(probeNoAvg)).thenReturn(null);
    }

    @Test
    void receivingProbNoAvg() {
        producer.send(new GenericMessage<PulseProbe>(probeNoAvg), bindingNameProducer);
        Message<byte[]> message = consumer.receive(10, bindingNameConsumer);
        assertNull(message);
    }

    @Test
    void receivingProbAvg() throws Exception {
        producer.send(new GenericMessage<PulseProbe>(probeAvgExpected), bindingNameProducer);
        Message<byte[]> message = consumer.receive(10, bindingNameConsumer);
        assertNotNull(message);
        ObjectMapper mapper = new ObjectMapper();
        PulseProbe probeAvgActual = mapper.readValue(message.getPayload(), PulseProbe.class);
        assertEquals(probeAvgExpected, probeAvgActual);
    }

    @Test
    void receivingNoRedisData() {
        System.out.println(realService.reduce(probeNoData));
        assertEquals(realService.reduce(probeNoData), 0);
    }

    @Test
    void receivingNoReduce() {
        assertNull(realService.reduce(probeNoAvg));
    }

//    @Test
//    void receivingReduce() {
//        assertEquals(10, realService.reduce(probeAvgExpected));
//    }
}
