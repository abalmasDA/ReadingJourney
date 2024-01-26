package service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import model.Account;
import model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class AccountServiceTest {

  private AccountService accountService;
  private List<Account> accounts;

  @BeforeEach
  public void setUp() {
    accountService = new AccountService();
    accounts = List.of(Account.builder()
            .country("Argentina")
            .lastName("Messi")
            .firstName("Lionel")
            .birthday(LocalDate.of(1987, 7, 20))
            .balance(500)
            .gender(Gender.MALE)
            .build(),
        Account.builder()
            .country("England")
            .lastName("Henderson")
            .firstName("Gordan")
            .birthday(LocalDate.of(1980, 8, 25))
            .balance(700)
            .gender(Gender.MALE)
            .build(),
        Account.builder()
            .country("USA")
            .lastName("Devise")
            .firstName("Ben")
            .birthday(LocalDate.of(1997, 2, 17))
            .balance(600)
            .gender(Gender.MALE)
            .build(),
        Account.builder()
            .country("USA")
            .lastName("Devise")
            .firstName("Alfredo")
            .birthday(LocalDate.of(1997, 2, 17))
            .balance(700)
            .gender(Gender.MALE)
            .build());
  }

  @Test
  void findExceedingBalanceTest() {
    int balance = 550;
    int expected = 3;
    List<Account> result = accountService.findExceedingBalance(accounts, balance);
    assertThat(result).isNotNull();
    assertThat(result.size()).isEqualTo(expected);
  }

  @Test
  void findUniqueCountryTest() {
    int expected = 3;
    Set<String> result = accountService.findUniqueCountry(accounts);
    assertThat(result).isNotNull();
    assertThat(result).doesNotHaveDuplicates();
    assertThat(result.size()).isEqualTo(expected);

  }

  @Test
  void hasYoungerThanTest() {
    int date = 1996;
    boolean expected = true;
    boolean result = accountService.hasYoungerThan(accounts, date);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void findSumBalanceByGenderTest() {
    double expected = 2500.0;
    double result = accountService.findSumBalanceByGender(accounts, Gender.MALE);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void groupByMonthTest() {
    Map<Integer, List<Account>> result = accountService.groupByMonth(accounts);
    assertThat(result).isNotNull();
    assertThat(result).isNotEmpty();
  }

  @Test
  void findAverageBalanceByCountryTest() {
    double expected = 650.0;
    OptionalDouble result = accountService.findAverBalByCountry(accounts, "USA");
    assertThat(result).isNotNull();
    assertThat(result.getAsDouble()).isEqualTo(expected);
  }

  @Test
  void getFullNamesTest() {
    List<List<Account>> listAccounts = List.of(accounts);
    String expected = "Lionel Messi, Gordan Henderson, Ben Devise, Alfredo Devise";
    String result = accountService.getFullNames(listAccounts);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void getSortedByNameTest() {
    List<Account> expected = List.of(accounts.get(3), accounts.get(2), accounts.get(1),
        accounts.get(0));
    List<Account> result = accountService.getSortedByName(accounts);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
    assertThat(result).containsExactlyElementsOf(expected);
  }

  @Test
  void getOldestTest() {
    Optional<Account> expected = Optional.ofNullable(accounts.get(1));
    Optional<Account> result = accountService.getOldest(accounts);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void getAverageBalanceByYearOfBirthTest() {
    Map<Integer, Double> expected = new HashMap<>();
    expected.put(1980, 700.0);
    expected.put(1987, 500.0);
    expected.put(1997, 650.0);
    Map<Integer, Double> result = accountService.getAverageBalanceByYearOfBirth(accounts);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
    assertThat(result).containsAllEntriesOf(expected);
  }

  @Test
  void getLongestLastTest() {
    Optional<Account> expected = Optional.ofNullable(accounts.get(1));
    Optional<Account> result = accountService.getLongestLastName(accounts);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }

}
