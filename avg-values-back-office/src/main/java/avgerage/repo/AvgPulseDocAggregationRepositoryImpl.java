package avgerage.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import telran.monitoring.entities.AvgPulseDoc;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
public class AvgPulseDocAggregationRepositoryImpl implements AvgPulseDocAggregationRepository{
    @Autowired
    MongoTemplate mongoTemplate;
    @Override
    public int getAvgValue(long patientId, LocalDateTime from, LocalDateTime to) {
        ArrayList<AggregationOperation> operations = new ArrayList<>();
        operations.add(match(Criteria.where("dateTime").gte(from).lte(to)));
        addOperationsForGettingValueByPatientId(operations, patientId);
        AggregationResults<Document> results = getResults(operations);
//        return results.getUniqueMappedResult() != null ?
//                results.getUniqueMappedResult().getInteger("averageValue") : 0;
        return 0;
    }

    @Override
    public int getAvgValue(long patientId) {
        ArrayList<AggregationOperation> operations = new ArrayList<>();
        addOperationsForGettingValueByPatientId(operations, patientId);
        AggregationResults<Document> results = getResults(operations);
//        return results.getUniqueMappedResult() != null ?
//                results.getUniqueMappedResult().getInteger("averageValue") : 0;
        return 0;
    }

    private void addOperationsForGettingValueByPatientId(ArrayList<AggregationOperation> operations, long patientId) {
        operations.add(group("patientId").avg("value").as("averageValue"));
        operations.add(match(Criteria.where("_id").is(patientId)));
        operations.add(project("averageValue").andExclude("_id"));
    }

    private AggregationResults<Document> getResults(ArrayList<AggregationOperation> operations) {
        Aggregation aggregation = newAggregation(operations);
        return mongoTemplate.aggregate(aggregation, AvgPulseDoc.class, Document.class);
    }
}
