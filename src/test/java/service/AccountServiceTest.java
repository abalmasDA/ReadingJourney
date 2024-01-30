package service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Stream;
import model.Account;
import model.Country;
import model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;


class AccountServiceTest {

  private AccountService accountService;
  private List<Account> accounts;

  @BeforeEach
  public void setUp() {
    accountService = new AccountService();
    accounts = List.of(Account.builder()
            .country(Country.ARGENTINA)
            .lastName("Messi")
            .firstName("Lionel")
            .birthday(LocalDate.of(1987, 7, 20))
            .balance(500.0)
            .gender(Gender.MALE)
            .build(),
        Account.builder()
            .country(Country.ENGLAND)
            .lastName("Henderson")
            .firstName("Gordan")
            .birthday(LocalDate.of(1980, 8, 25))
            .balance(700.0)
            .gender(Gender.MALE)
            .build(),
        Account.builder()
            .country(Country.USA)
            .lastName("Devise")
            .firstName("Ben")
            .birthday(LocalDate.of(1997, 2, 17))
            .balance(600.0)
            .gender(Gender.MALE)
            .build(),
        Account.builder()
            .country(Country.USA)
            .lastName("Devise")
            .firstName("Alfredo")
            .birthday(LocalDate.of(1997, 2, 17))
            .balance(700.0)
            .gender(Gender.MALE)
            .build());
  }

  @Test
  void findExceedingBalanceSingleValueTest() {
    double balance = 550.0;
    int expected = 3;
    List<Account> result = accountService.findExceedingBalance(accounts, balance);
    assertThat(result).isNotNull();
    assertThat(result.size()).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({
      "500.0, 3",
      "695.0, 2",
      "710.0, 0",
      "800.0, 0"
  })
  void findExceedingBalanceMultipleValuesTest(double balance, int expected) {
    List<Account> result = accountService.findExceedingBalance(accounts, balance);
    assertThat(result).isNotNull();
    assertThat(result.size()).isEqualTo(expected);
  }

  @Test
  void findExceedingBalanceNegativeBalanceTest() {
    int balance = -100;
    assertThatThrownBy(() -> accountService.findExceedingBalance(accounts, balance))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Balance cannot be negative");
  }


  @Test
  void findUniqueCountrySingleValueTest() {
    int expected = 3;
    Set<Country> result = accountService.findUniqueCountry(accounts);
    assertThat(result).isNotNull();
    assertThat(result).doesNotHaveDuplicates();
    assertThat(result.size()).isEqualTo(expected);
  }

  @Test
  void hasYoungerThanSingleValueTest() {
    int year = 1996;
    boolean expected = true;
    boolean result = accountService.hasYoungerThan(accounts, year);
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({
      "1996, true",
      "1997, false",
      "1980, true",
      "1975, true",
      "2000, false"
  })
  void hasYoungerThanMultipleValuesTest(int year, boolean expected) {
    boolean result = accountService.hasYoungerThan(accounts, year);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void hasYoungerThanNegativeYearTest() {
    int year = -1985;
    assertThatThrownBy(() -> accountService.hasYoungerThan(accounts, year))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Year cannot be negative");
  }

  @Test
  void findSumBalanceByGenderSingleValueTest() {
    double expected = 2500.0;
    double result = accountService.findSumBalanceByGender(accounts, Gender.MALE);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({
      "MALE, 2500.0",
      "FEMALE, 0.0"
  })
  void findSumBalanceByGenderMultipleValuesTest(String genderExpected, double expected) {
    Gender gender = Gender.valueOf(genderExpected);
    double result = accountService.findSumBalanceByGender(accounts, gender);
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void groupByMonthSingleValueTest() {
    Map<Integer, List<Account>> result = accountService.groupByMonth(accounts);
    assertThat(result).isNotNull();
    assertThat(result).isNotEmpty();
  }

  @ParameterizedTest
  @CsvSource({
      "7, 1",
      "2, 2",
      "8, 1"
  })
  void groupByMonthMultipleValuesTest(int month, int expectedGroupSize) {
    Map<Integer, List<Account>> result = accountService.groupByMonth(accounts);
    assertThat(result)
        .containsKey(month)
        .satisfies(group -> assertThat(group.get(month)).hasSize(expectedGroupSize));
  }

  @Test
  void findAverageBalanceByCountrySingleValueTest() {
    double expected = 650.0;
    OptionalDouble result = accountService.findAverBalByCountry(accounts, Country.USA);
    assertThat(result).isNotNull();
    assertThat(result.getAsDouble()).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource({
      "USA, 650.0",
      "ENGLAND, 700.0",
      "ARGENTINA, 500.0"
  })
  void findAverageBalanceByCountryMultipleValuesTest(String countryExpected, double expected) {
    Country country = Country.valueOf(countryExpected);
    OptionalDouble result = accountService.findAverBalByCountry(accounts, country);
    assertThat(result).isNotNull();
    assertThat(result.getAsDouble()).isEqualTo(expected);
  }

  @Test
  void findAverageBalanceByCountryNullTest() {
    Country country = null;
    assertThatThrownBy(() -> accountService.findAverBalByCountry(accounts, country))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Country parameter must not be null");
  }

  @Test
  void getFullNamesSingleValueTest() {
    List<List<Account>> listAccounts = List.of(accounts);
    String expected = "Lionel Messi, Gordan Henderson, Ben Devise, Alfredo Devise";
    String result = accountService.getFullNames(listAccounts);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @MethodSource("fullNameProvider")
  void getFullNamesMultipleValuesTest(List<List<Account>> accounts, String expected) {
    String result = accountService.getFullNames(accounts);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }

  private static Stream<Arguments> fullNameProvider() {
    return Stream.of(
        Arguments.of(
            List.of(
                List.of(
                    Account.builder()
                        .firstName("Ivan")
                        .lastName("Ivanov")
                        .country(Country.ARGENTINA)
                        .birthday(LocalDate.of(1987, 7, 20))
                        .balance(500.0)
                        .gender(Gender.MALE)
                        .build(),
                    Account.builder()
                        .firstName("Gordan")
                        .lastName("Henderson")
                        .country(Country.ENGLAND)
                        .birthday(LocalDate.of(1980, 8, 25))
                        .balance(700.0)
                        .gender(Gender.MALE)
                        .build()
                ),
                List.of(
                    Account.builder()
                        .firstName("Ben")
                        .lastName("Devise")
                        .country(Country.USA)
                        .birthday(LocalDate.of(1997, 2, 17))
                        .balance(600.0)
                        .gender(Gender.MALE)
                        .build(),
                    Account.builder()
                        .firstName("Alfredo")
                        .lastName("Devise")
                        .country(Country.USA)
                        .birthday(LocalDate.of(1997, 2, 17))
                        .balance(700.0)
                        .gender(Gender.MALE)
                        .build()
                )
            ),
            "Ivan Ivanov, Gordan Henderson, Ben Devise, Alfredo Devise"
        )
    );
  }

  @Test
  void getSortedByNameSingleValueTest() {
    List<Account> expected = List.of(accounts.get(3), accounts.get(2), accounts.get(1),
        accounts.get(0));
    List<Account> result = accountService.getSortedByName(accounts);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
    assertThat(result).containsExactlyElementsOf(expected);
  }

  @ParameterizedTest
  @MethodSource("accountProvider")
  void getSortedByNameMultipleValuesTest(List<Account> unsorted, List<Account> expected) {
    List<Account> result = accountService.getSortedByName(unsorted);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
    assertThat(result).containsExactlyElementsOf(expected);
  }

  private static Stream<Arguments> accountProvider() {
    return Stream.of(
        Arguments.of(
            List.of(
                Account.builder()
                    .lastName("Smith")
                    .firstName("Jane")
                    .country(Country.USA)
                    .birthday(LocalDate.of(1990, 1, 1))
                    .balance(300.0)
                    .gender(Gender.FEMALE)
                    .build(),
                Account.builder()
                    .lastName("Dalglish")
                    .firstName("John")
                    .country(Country.ENGLAND)
                    .birthday(LocalDate.of(1985, 5, 15))
                    .balance(400.0)
                    .gender(Gender.MALE)
                    .build(),
                Account.builder()
                    .lastName("Asher")
                    .firstName("Cany")
                    .country(Country.ENGLAND)
                    .birthday(LocalDate.of(1999, 5, 17))
                    .balance(5000.0)
                    .gender(Gender.MALE)
                    .build()
            ),
            List.of(
                Account.builder()
                    .lastName("Asher")
                    .firstName("Cany")
                    .country(Country.ENGLAND)
                    .birthday(LocalDate.of(1999, 5, 17))
                    .balance(5000.0)
                    .gender(Gender.MALE)
                    .build(),
                Account.builder()
                    .lastName("Dalglish")
                    .firstName("John")
                    .country(Country.ENGLAND)
                    .birthday(LocalDate.of(1985, 5, 15))
                    .balance(400.0)
                    .gender(Gender.MALE)
                    .build(),
                Account.builder()
                    .lastName("Smith")
                    .firstName("Jane")
                    .country(Country.USA)
                    .birthday(LocalDate.of(1990, 1, 1))
                    .balance(300.0)
                    .gender(Gender.FEMALE)
                    .build()
            )
        )
    );
  }

  @Test
  void getOldestSingleValueTest() {
    Optional<Account> expected = Optional.ofNullable(accounts.get(1));
    Optional<Account> result = accountService.getOldest(accounts);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @MethodSource("oldestAccountProvider")
  void getOldestMultipleValuesTest(List<Account> accounts, Account expected) {
    Optional<Account> result = accountService.getOldest(accounts);
    assertThat(result).isNotNull();
    assertThat(result).contains(expected);
  }

  private static Stream<Arguments> oldestAccountProvider() {
    return Stream.of(
        Arguments.of(
            List.of(
                Account.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .birthday(LocalDate.of(1980, 1, 1))
                    .build(),
                Account.builder()
                    .firstName("Jane")
                    .lastName("Smith")
                    .birthday(LocalDate.of(1970, 1, 1))
                    .build(),
                Account.builder()
                    .firstName("Piter")
                    .lastName("Parker")
                    .birthday(LocalDate.of(1950, 1, 1))
                    .build()
            ),
            Account.builder()
                .firstName("Piter")
                .lastName("Parker")
                .birthday(LocalDate.of(1950, 1, 1))
                .build()
        ),
        Arguments.of(
            List.of(
                Account.builder()
                    .firstName("Alice")
                    .lastName("Johnson")
                    .birthday(LocalDate.of(1990, 1, 1))
                    .build(),
                Account.builder()
                    .firstName("Bob")
                    .lastName("Lee")
                    .birthday(LocalDate.of(1989, 5, 15))
                    .build()
            ),
            Account.builder()
                .firstName("Bob")
                .lastName("Lee")
                .birthday(LocalDate.of(1989, 5, 15))
                .build()
        )
    );
  }

  @Test
  void getAverageBalanceByYearOfBirthSingleValueTest() {
    Map<Integer, Double> expected = new HashMap<>();
    expected.put(1980, 700.0);
    expected.put(1987, 500.0);
    expected.put(1997, 650.0);
    Map<Integer, Double> result = accountService.getAverageBalanceByYearOfBirth(accounts);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
    assertThat(result).containsAllEntriesOf(expected);
  }

  @ParameterizedTest
  @MethodSource("averageProvider")
  void getAverageBalanceByYearOfBirthMultipleValuesTest(List<Account> accounts,
      Map<Integer, Double> expected) {
    Map<Integer, Double> result = accountService.getAverageBalanceByYearOfBirth(accounts);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
    assertThat(result).containsAllEntriesOf(expected);
  }

  private static Stream<Arguments> averageProvider() {
    return Stream.of(
        Arguments.of(
            List.of(
                Account.builder()
                    .birthday(LocalDate.of(1980, 1, 1))
                    .balance(700.0)
                    .build(),
                Account.builder()
                    .birthday(LocalDate.of(1987, 1, 1))
                    .balance(500.0)
                    .build(),
                Account.builder()
                    .birthday(LocalDate.of(1987, 1, 1))
                    .balance(500.0)
                    .build(),
                Account.builder()
                    .birthday(LocalDate.of(1989, 1, 1))
                    .balance(500.0)
                    .build(),
                Account.builder()
                    .birthday(LocalDate.of(1997, 1, 1))
                    .balance(600.0)
                    .build(),
                Account.builder()
                    .birthday(LocalDate.of(1997, 1, 1))
                    .balance(700.0)
                    .build()
            ),
            new HashMap<Integer, Double>() {{
              put(1980, 700.0);
              put(1987, 500.0);
              put(1989, 500.0);
              put(1997, 650.0);
            }}
        )
    );
  }

  @Test
  void getLongestLastSingleValueTest() {
    Optional<Account> expected = Optional.ofNullable(accounts.get(1));
    Optional<Account> result = accountService.getLongestLastName(accounts);
    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @MethodSource("lastNameProvider")
  void getLongestLastNameMultipleValuesTest(List<Account> accounts, Account expected) {
    Optional<Account> result = accountService.getLongestLastName(accounts);
    assertThat(result).isNotNull();
    assertThat(result).contains(expected);
  }

  private static Stream<Arguments> lastNameProvider() {
    return Stream.of(
        Arguments.of(
            List.of(
                Account.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .build(),
                Account.builder()
                    .firstName("Jane")
                    .lastName("Smithson")
                    .build(),
                Account.builder()
                    .firstName("Alex")
                    .lastName("Petrovich")
                    .build(),
                Account.builder()
                    .firstName("Lukas")
                    .lastName("Digne")
                    .build()
            ),
            Account.builder()
                .firstName("Alex")
                .lastName("Petrovich")
                .build()
        ),
        Arguments.of(
            List.of(
                Account.builder()
                    .firstName("Alice")
                    .lastName("Trichardson")
                    .build(),
                Account.builder()
                    .firstName("Bob")
                    .lastName("Lee")
                    .build(),
                Account.builder()
                    .firstName("Charlie")
                    .lastName("Blue")
                    .build()
            ),
            Account.builder()
                .firstName("Alice")
                .lastName("Trichardson")
                .build()
        )
    );
  }
}
