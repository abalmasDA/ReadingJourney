package com.abalmas.dmytro.repository;

import com.abalmas.dmytro.exception.AccountNotFoundException;
import com.abalmas.dmytro.model.Account;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

  private final List<Account> accounts = new ArrayList<>();

  @Override
  public List<Account> findAll() {
    return accounts;
  }

  @Override
  public Optional<Account> findById(long id) {
    return accounts.stream()
        .filter(a -> a.getId() == id)
        .findFirst();
  }


  @Override
  public Account add(Account account) {
    accounts.add(account);
    return account;
  }

  @Override
  public Account update(long id, Account accountToUpdate) {
    Optional<Account> possibleAccount = findById(id);
    return possibleAccount.map(account -> {
      account.setFirstName(accountToUpdate.getFirstName());
      account.setLastName(accountToUpdate.getLastName());
      account.setCountry(accountToUpdate.getCountry());
      account.setBirthday(accountToUpdate.getBirthday());
      account.setGender(accountToUpdate.getGender());
      return account;
    }).orElseThrow(() -> new AccountNotFoundException("Account not found"));
  }

  @Override
  public void delete(long id) {
    Optional<Account> account = findById(id);
    account.ifPresent(accounts::remove);
  }

}
