package telran.imitator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import telran.monitoring.model.PulseProbe;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class PulseProbeImitatorImpl implements PulseProbesImitator{

    @Value("${app.patients.amount: 10}")
    int patients;
    @Value("${app.pulse.value.min: 60}")
    int pulseMin;
    @Value("${app.pulse.value.max: 100}")
    int pulseMax;
    @Value("${app.jump.probability: 5}")
    int probability; // percent
    @Value("${app.jump.multiplier: 35}")
    int jumpMultiplier;
    @Value("${app.no-jump.multiplier: 5}")
    int noJumpMultiplier;
    int seqNumber = 1;
    HashMap<Long, HashMap<Long, Integer>> fullData = new HashMap<>(); // {id: {timestamp: probeValue}}
    HashMap<Long, Long> timestampData = new HashMap<>(); // {id: timestamp} - last update
    private static Logger LOG = LoggerFactory.getLogger(PulseProbeImitatorImpl.class);

    @Override
    public PulseProbe nextProbe() {
        long curId = getRandomId();
        long timestamp = Instant.now().toEpochMilli();
        int prevPulseValue = 0;
        if (fullData.get(curId) != null) {
            prevPulseValue = updateData(curId, timestamp);
        }
        else {
            fullData.put(curId, getFirstProbe(curId, timestamp));
            timestampData.put(curId, timestamp);
        }
        PulseProbe probe = new PulseProbe(curId, timestamp, seqNumber++, fullData.get(curId).get(timestamp));
        LOG.info(String.format("%d - Pulse value of patient with id - %d is %d, previous value was %d, timestamp: %s",
                probe.sequenceNumber, probe.patientId, probe.value, prevPulseValue,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(probe.timestamp), ZoneId.systemDefault())));
        return probe;
    }

    private Long getRandomId() {
        return ThreadLocalRandom.current().nextLong(patients) + 1;
    }

    private HashMap<Long, Integer> getFirstProbe(long id, long timestamp) {
        HashMap<Long, Integer> res = new HashMap<>();
        timestampData.put(id, timestamp);
        res.put(timestamp, ThreadLocalRandom.current().nextInt(pulseMin, pulseMax + 1));
        return res;
    }

    private Integer updateData(long curId, long timestamp) {
        long lastUpdate = timestampData.get(curId);
        int lastPulseValue = fullData.get(curId).get(lastUpdate);
        boolean hasJump = ThreadLocalRandom.current().nextDouble() < (probability / 100.0);
        timestampData.replace(curId, timestamp);
        int factor = ThreadLocalRandom.current().nextDouble(1) > 0.5 ? 1 : -1;
        return hasJump ? updateDataAfterJump(curId, lastUpdate, lastPulseValue, factor) :
                updateDataWithoutJump(curId, lastUpdate, lastPulseValue, factor);
    }


    private Integer updateDataAfterJump(long curId, long lastUpdate, int lastPulseValue, int factor) {
        LOG.warn(String.format("Patient with id - %d has jump!!!", curId));
        return fullData.get(curId).replace(lastUpdate, lastPulseValue + jumpMultiplier * factor);
    }

    private Integer updateDataWithoutJump(long curId, long lastUpdate, int lastPulseValue, int factor) {
        return fullData.get(curId).replace(lastUpdate, lastPulseValue + noJumpMultiplier * factor);
    }
}
