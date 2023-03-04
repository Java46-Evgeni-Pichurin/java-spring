package telran.spring.accounting.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.spring.accounting.entities.AccountEntity;

import java.util.List;
import java.time.LocalDateTime;

public interface AccountRepository extends MongoRepository<AccountEntity, String> {
    List<AccountEntity> findByRevokedIsFalseAndExpirationGreaterThan(LocalDateTime time);

}
