package telran.spring.company.service.accounts;

import telran.spring.company.model.Account;

import java.util.HashMap;

public interface AccountingService {
    Boolean add(Account account);

    Boolean deleteAccount(String username);

    Boolean updateAccount(Account account);

    Boolean isExist(String username);

    void readAccounts(String path);

    void writeAccounts(String path);

    HashMap<String, Account> getAll();
}
