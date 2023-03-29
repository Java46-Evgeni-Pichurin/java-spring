package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;

import telran.monitoring.entities.redis.ProbesList;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.repo.AvgReducerRepository;
import telran.monitoring.service.AvgReducerService;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class AvgReducerServiceTest {
    static final Long PATIENT_NO_REDIS_DATA = 123L;
    private static final Long PATIENT_NO_AVG = 125L;
    private static final int VALUE = 100;
    private static final Long PATIENT_AVG = 127L;

    @Autowired
    AvgReducerService service;
    @MockBean
    AvgReducerRepository avgReducerRepository;

    static ProbesList listNoAvgValue = new ProbesList(PATIENT_NO_AVG);
    static ProbesList listAvgValue = new ProbesList(PATIENT_AVG);
    static List<Integer> emptyProbeList;
    static List<Integer> oneProbeList;

    @BeforeAll
    static void setUp() {
        emptyProbeList = listNoAvgValue.getPulseValues();
        oneProbeList = listAvgValue.getPulseValues();
        oneProbeList.add(VALUE);
    }

    @BeforeEach
    void redisMocking() {
        when(avgReducerRepository.findById(PATIENT_NO_REDIS_DATA)).thenReturn(Optional.empty());
        when(avgReducerRepository.findById(PATIENT_NO_AVG)).thenReturn(Optional.of(listNoAvgValue));
        when(avgReducerRepository.findById(PATIENT_AVG)).thenReturn(Optional.of(listAvgValue));
    }

    @Test
    void noRedisDataTest() {
        assertNull(service.reduce(new PulseProbe(PATIENT_NO_REDIS_DATA, 0, 0, VALUE)));
    }

    @Test
    void noAvgTest() {
        assertNull(service.reduce(new PulseProbe(PATIENT_NO_AVG, 0, 0, VALUE)));
        assertEquals(1, emptyProbeList.size());
    }

    @Test
    void avgTest() {
        assertEquals(VALUE, service.reduce(new PulseProbe(PATIENT_AVG, 0, 0, VALUE)));
        assertEquals(0, oneProbeList.size());
    }
}
