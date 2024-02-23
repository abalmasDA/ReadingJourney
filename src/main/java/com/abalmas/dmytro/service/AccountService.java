package main.java.com.abalmas.dmytro.service;


import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;
import main.java.com.abalmas.dmytro.exception.AccountNotFoundException;
import main.java.com.abalmas.dmytro.model.Account;
import main.java.com.abalmas.dmytro.model.enums.Country;
import main.java.com.abalmas.dmytro.model.enums.Gender;
import main.java.com.abalmas.dmytro.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  private final AccountRepository accountRepository;

  public AccountService(
      @Qualifier("accountRepositoryHiberImpl") AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public List<Account> findAll() {
    return accountRepository.findAll();
  }

  public Optional<Account> findById(long id) {
    return Optional.ofNullable(accountRepository.findById(id)
        .orElseThrow(() -> new AccountNotFoundException("Account not found")));
  }

  public Account add(Account account) {
    return accountRepository.add(account);
  }

  public Account update(long id, Account account) {
    return accountRepository.update(id, account);
  }

  public void delete(long id) {
    accountRepository.delete(id);
  }

  public List<Account> findExceedingBalance(List<Account> accounts,
      double balance) {
    if (balance < 0) {
      throw new IllegalArgumentException("Balance cannot be negative");
    }
    return accounts.stream()
        .filter(account -> account.getBalance() > balance)
        .toList();
  }

  public Set<Country> findUniqueCountry(List<Account> accounts) {
    return accounts.stream()
        .map(Account::getCountry)
        .collect(Collectors.toSet());
  }

  public boolean hasYoungerThan(List<Account> accounts, long year) {
    if (year < 0) {
      throw new IllegalArgumentException("Year cannot be negative");
    }
    return accounts.stream()
        .anyMatch(account -> account.getBirthday().getYear() > year);
  }

  public double findSumBalanceByGender(List<Account> accounts, Gender gender) {
    return accounts.stream()
        .filter(account -> gender.equals(account.getGender()))
        .mapToDouble(Account::getBalance)
        .sum();
  }

  public Map<Integer, List<Account>> groupByMonth(List<Account> accounts) {
    return accounts.stream()
        .collect(
            Collectors.groupingBy(account -> account.getBirthday().getMonthValue()));
  }

  public OptionalDouble findAverBalByCountry(List<Account> accounts, Country country) {
    if (country == null) {
      throw new IllegalArgumentException("Country parameter must not be null");
    }
    return accounts.stream()
        .filter(account -> country.equals(account.getCountry()))
        .mapToDouble(Account::getBalance)
        .average();
  }

  public String getFullNames(List<List<Account>> accounts) {
    return accounts.stream()
        .flatMap(List::stream)
        .map(account -> account.getFirstName() + " " + account.getLastName())
        .collect(Collectors.joining(", "));
  }

  public List<Account> getSortedByName(List<Account> accounts) {
    return accounts.stream()
        .sorted(Comparator.comparing(Account::getLastName)
            .thenComparing(Account::getFirstName))
        .collect(Collectors.toList());
  }

  public Optional<Account> getOldest(List<Account> accounts) {
    return accounts.stream()
        .min(Comparator.comparing(Account::getBirthday));
  }

  public Map<Integer, Double> getAverageBalanceByYearOfBirth(List<Account> accounts) {
    return accounts.stream()
        .collect(Collectors.groupingBy(
            account -> account.getBirthday().getYear(),
            Collectors.averagingDouble(Account::getBalance)
        ));
  }

  public Optional<Account> getLongestLastName(List<Account> accounts) {
    return accounts.stream()
        .max(Comparator.comparingInt(account -> account.getLastName().length()));
  }
}
