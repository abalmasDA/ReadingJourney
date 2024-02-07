package com.abalmas.dmytro.repository;

import com.abalmas.dmytro.model.Entity.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {

  List<Account> findAll();

  Optional<Account> findById(int id);

  Account add(Account account);

  Account update(int id, Account account);

  void delete(int id);

}
