package telran.monitoring.repo;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import telran.monitoring.entities.mongodb.AvgPulseDoc;
import telran.monitoring.service.AvgPulseValuesService;

import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static telran.monitoring.entities.mongodb.AvgPulseDoc.*;

public class BackOfficeCustomRepositoryImpl implements BackOfficeCustomRepository {
    static Logger LOG = LoggerFactory.getLogger(AvgPulseValuesService.class);
    private static final String AVG_VALUE = "avgValue";

    MongoTemplate mongoTemplate;

    public BackOfficeCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public int getAvgValue(long patientId) {
        MatchOperation matchOperation = match(Criteria.where(PATIENT_ID).is(patientId));
        return avgValueRequest(matchOperation);
    }

    @Override
    public int getAvgValue(long patientId, LocalDateTime from, LocalDateTime to) {
        Criteria criteria = Criteria.where(PATIENT_ID).is(patientId)
                .andOperator(Criteria.where(DATE_TIME).gte(from).lte(to));
        LOG.trace("all values of patient {} are {}", patientId,
                mongoTemplate.find(new Query(criteria), AvgPulseDoc.class));
        MatchOperation match = match(criteria);
        return avgValueRequest(match);
    }

    private int avgValueRequest(MatchOperation matchOperation) {
        GroupOperation groupOperation = group().avg(PULSE_VALUE).as(AVG_VALUE);
        Aggregation pipeline = newAggregation(matchOperation, groupOperation);
        Document document = mongoTemplate.aggregate(pipeline, AvgPulseDoc.class, Document.class)
                .getUniqueMappedResult();
        return document == null ? 0 : document.getDouble(AVG_VALUE).intValue();
    }
}
