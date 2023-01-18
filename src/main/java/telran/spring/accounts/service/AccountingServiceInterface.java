package telran.spring.accounts.service;

import telran.spring.accounts.dto.Account;

import java.util.HashMap;

public interface AccountingServiceInterface {
    Boolean add(Account account);

    Boolean deleteAccount(String username);

    Boolean updateAccount(Account account);

    Boolean isExist(String username);

    void readAccounts(String path);

    void writeAccounts(String path);

    HashMap<String, Account> getAll();
}
