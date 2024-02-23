package com.abalmas.dmytro.repository;

import com.abalmas.dmytro.exception.AccountNotFoundException;
import com.abalmas.dmytro.repository.AccountRepository;
import com.abalmas.dmytro.model.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepositoryHiberImpl implements AccountRepository {

  private final SessionFactory sessionFactory;

  public AccountRepositoryHiberImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public List<Account> findAll() {
    try (Session session = sessionFactory.openSession()) {
      return session.createQuery("FROM Account", Account.class).getResultList();
    }
  }

  @Override
  public Optional<Account> findById(long id) {
    try (Session session = sessionFactory.openSession()) {
      return Optional.ofNullable(session.get(Account.class, id));
    }
  }

  @Override
  public Account add(Account accountToSave) {
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.persist(accountToSave);
      session.getTransaction().commit();
      return accountToSave;
    }
  }

  @Override
  public Account update(long id, Account accountToUpdate) {
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      Account account = session.get(Account.class, id);
      if (account != null) {
        account.setFirstName(accountToUpdate.getFirstName());
        account.setLastName(accountToUpdate.getLastName());
        account.setCountry(accountToUpdate.getCountry());
        account.setBirthday(accountToUpdate.getBirthday());
        account.setGender(accountToUpdate.getGender());
        session.update(account);
        session.getTransaction().commit();
        return account;
      } else {
        throw new AccountNotFoundException("Account not found");
      }
    }
  }

  @Override
  public void delete(long id) {
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      Account account = session.get(Account.class, id);
      if (account != null) {
        session.delete(account);
        session.getTransaction().commit();
      }
    }
  }
}



