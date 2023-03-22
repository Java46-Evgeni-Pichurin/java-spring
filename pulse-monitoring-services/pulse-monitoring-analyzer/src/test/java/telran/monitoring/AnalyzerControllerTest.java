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

import telran.monitoring.entities.LastProbe;
import telran.monitoring.model.*;
import telran.monitoring.repo.LastProbeRepository;
import telran.monitoring.service.AnalyzerService;

import java.util.Optional;

@SpringBootTest(classes = AnalyzerAppl.class)
@Import(TestChannelBinderConfiguration.class)
//loading to Application Context producer/consumer for Spring Cloud Messaging
public class AnalyzerControllerTest {
    private static final long PATIENT_ID_NO_JUMP = 123;
    private static final long PATIENT_ID_JUMP = 124;
    private static final long PATIENT_NO_REDIS_DATA = 125;
    private static final int VALUE = 100;
    private static final int VALUE_JUMP = 240;

    @MockBean
    AnalyzerService service;
    @Autowired
    AnalyzerService realService;
    @MockBean
    LastProbeRepository probesRepository;
    @Autowired
    InputDestination producer;
    @Autowired
    OutputDestination consumer;
    @Value("${app.jumps.binding.producer.name}")
    String bindingNameProducer;
    @Value("${app.jumps.binding.consumer.name}")
    String bindingNameConsumer;
    PulseProbe probeNoJump = new PulseProbe(PATIENT_ID_NO_JUMP, 0, 0, VALUE);
    PulseProbe probeJump = new PulseProbe(PATIENT_ID_JUMP, 0, 0, VALUE);
    PulseProbe probeFirst = new PulseProbe(PATIENT_NO_REDIS_DATA, 0, 0, VALUE);
    PulseJump jump = new PulseJump(PATIENT_ID_JUMP, VALUE, VALUE_JUMP);
    LastProbe lastProbeNoJump = new LastProbe(PATIENT_ID_JUMP, VALUE);
    LastProbe lastProbeJump = new LastProbe(PATIENT_ID_JUMP, VALUE_JUMP);

    @BeforeEach
    void mockingService() {
        //No REDIS data
        when(probesRepository.findById(PATIENT_NO_REDIS_DATA)).thenReturn(Optional.empty());
        //There is REDIS data but no jump
        when(probesRepository.findById(PATIENT_ID_NO_JUMP)).thenReturn(Optional.of(lastProbeNoJump));
        //There is REDIS data with a jump
        when(probesRepository.findById(PATIENT_ID_JUMP)).thenReturn(Optional.of(lastProbeJump));

        when(service.processPulseProbe(probeJump)).thenReturn(jump);
        when(service.processPulseProbe(probeNoJump)).thenReturn(null);
    }

    @Test
    void receivingProbNoJump() {
        producer.send(new GenericMessage<PulseProbe>(probeNoJump), bindingNameProducer);
        Message<byte[]> message = consumer.receive(10, bindingNameConsumer);
        assertNull(message);
    }

    @Test
    void receivingProbJump() throws Exception {
        producer.send(new GenericMessage<PulseProbe>(probeJump), bindingNameProducer);
        Message<byte[]> message = consumer.receive(10, bindingNameConsumer);
        assertNotNull(message);
        ObjectMapper mapper = new ObjectMapper();
        PulseJump jump = mapper.readValue(message.getPayload(), PulseJump.class);
        assertEquals(this.jump, jump);
    }

    @Test
    void receivingRedisDataJump() {
        assertEquals(realService.processPulseProbe(probeJump), jump);
    }

    @Test
    void receivingRedisDataNoJump() {
        assertNull(realService.processPulseProbe(probeNoJump));
    }

    @Test
    void receivingFirstProbe() {
        assertNull(realService.processPulseProbe(probeFirst));
    }
}
