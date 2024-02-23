package main.java.com.abalmas.dmytro.repository;

import java.util.List;
import java.util.Optional;
import main.java.com.abalmas.dmytro.model.Account;

public interface AccountRepository {

  List<Account> findAll();

  Optional<Account> findById(long id);

  Account add(Account account);

  Account update(long id, Account account);

  void delete(long id);

}
