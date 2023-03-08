package telran.spring.accounting.repo;

import java.util.List;

public interface AccountAggregationRepository {
    long getMaxRoles();
    List<String> getAllAccountsWithMaxRoles();
    int getMaxRolesOccurrenceCount();
    List<String> getAllRolesWithMaxOccurrrence();
    int getActiveMinRolesOccurrenceCount();
}
