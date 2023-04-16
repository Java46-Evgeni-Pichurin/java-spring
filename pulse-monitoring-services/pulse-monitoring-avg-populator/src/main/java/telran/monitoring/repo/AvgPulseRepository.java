package telran.monitoring.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import telran.monitoring.entities.mongodb.AvgPulseDoc;

public interface AvgPulseRepository extends MongoRepository<AvgPulseDoc, ObjectId> {
}