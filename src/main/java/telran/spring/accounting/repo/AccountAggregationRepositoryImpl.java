package telran.spring.accounting.repo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoExpression;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.springframework.stereotype.Repository;

import telran.spring.accounting.entities.AccountEntity;

@Repository
public class AccountAggregationRepositoryImpl implements AccountAggregationRepository {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public long getMaxRoles() {
        ArrayList<AggregationOperation> operations = new ArrayList<>();
        operations.add(unwind("roles"));
        operations.add(group("email").count().as("count"));
        operations.add(group().max("count").as("maxCount"));
        return createDoc(operations).getUniqueMappedResult().getInteger("maxCount");
    }

    @Override
    public List<String> getAllAccountsWithMaxRoles() {
        ArrayList<AggregationOperation> operations = new ArrayList<>();
        operations.add(match(AggregationExpression.from(
                MongoExpression.create(String.format("{roles: {$size: %d}}", getMaxRoles())))));
        operations.add(project("email"));
        return createDoc(operations).getMappedResults().stream().map(doc -> doc.get("email").toString()).toList();
    }

    @Override
    public int getMaxRolesOccurrenceCount() {
        ArrayList<AggregationOperation> operations = new ArrayList<>();
        operations.add(unwind("roles"));
        operations.add(group("roles").count().as("occurrenceCount"));
        operations.add(group().max("occurrenceCount").as("maxOccurrenceCount"));
        return createDoc(operations).getUniqueMappedResult().getInteger("maxOccurrenceCount");
    }

    @Override
    public List<String> getAllRolesWithMaxOccurrrence() {
        ArrayList<AggregationOperation> operations = new ArrayList<>();
        operations.add(unwind("roles"));
        operations.add(group("roles").count().as("occurrenceCount"));
        operations.add(match(AggregationExpression.from(
                MongoExpression.create(String.format("{occurrenceCount: %d}", getMaxRolesOccurrenceCount())))));
        return createDoc(operations).getMappedResults().stream().map(doc -> doc.get("_id").toString()).toList();
    }

    @Override
    public int getActiveMinRolesOccurrenceCount() {
        ArrayList<AggregationOperation> operations = new ArrayList<>();
        operations.add(unwind("roles"));
        operations.add(match(AggregationExpression.from(
                MongoExpression.create("{revoked: false}"))));
        operations.add(group("roles").count().as("occurrenceCount"));
        operations.add(group().min("occurrenceCount").as("minOccurrenceCount"));
        return createDoc(operations).getUniqueMappedResult().getInteger("minOccurrenceCount");
    }

    private AggregationResults<Document> createDoc(ArrayList<AggregationOperation> operations) {
        Aggregation pipeline = newAggregation(operations);
        return mongoTemplate.aggregate(pipeline,
                AccountEntity.class, Document.class);
    }
}
