package com.abalmas.dmytro.repository;

import com.abalmas.dmytro.model.Entity.Account;

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
  public Optional<Account> findById(int id) {
    return accounts.stream()
        .filter(account -> account.getId() == id)
        .findFirst();
  }

  @Override
  public Account add(Account account) {
    accounts.add(account);
    return account;
  }

  @Override
  public Account update(int id, Account accountToUpdate) {
    Optional<Account> possibleAccount = findById(id);
    return possibleAccount.map(account -> {
      account.setFirstName(accountToUpdate.getFirstName());
      account.setLastName(accountToUpdate.getLastName());
      account.setCountry(accountToUpdate.getCountry());
      account.setBirthday(accountToUpdate.getBirthday());
      account.setGender(accountToUpdate.getGender());
      return account;
    }).orElse(null);
  }

  @Override
  public void delete(int id) {
    Optional<Account> account = findById(id);
    account.ifPresent(accounts::remove);
  }

}
