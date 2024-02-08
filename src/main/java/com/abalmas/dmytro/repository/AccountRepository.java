package com.abalmas.dmytro.repository;

import com.abalmas.dmytro.model.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {

  List<Account> findAll();

  Optional <Account> findById(long id);

  Account add(Account account);

  Account update(long id, Account account);

  void delete(long id);

}
