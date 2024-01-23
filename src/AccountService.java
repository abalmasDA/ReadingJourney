import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountService {

  public static List<Account> findExceedingBalance(List<Account> accounts, int balance) {
    return accounts.stream()
        .filter(account -> account.getBalance() > balance)
        .toList();
  }


  public static Set<String> findUniqueCountry(List<Account> accounts) {
    return accounts.stream()
        .map(Account::getCountry)
        .collect(Collectors.toSet());
  }

  public static boolean hasYoungerThan(List<Account> accounts, int date) {
    return accounts.stream()
        .anyMatch(account -> account.getBirthday().getYear() > date);
  }

  public static double findSumBalanceByGender(List<Account> accounts) {
    return accounts.stream()
        .filter(account -> Gender.MALE.equals(account.getGender()))
        .mapToDouble(Account::getBalance)
        .sum();
  }

  public static Map<Integer, List<Account>> groupByMonth(List<Account> accounts) {
    return accounts.stream()
        .collect(Collectors.groupingBy(account -> account.getBirthday().getMonthValue()));
  }

  public static OptionalDouble findAverBalByCountry(List<Account> accounts, String country) {
    return accounts.stream()
        .filter(account -> country.equals(account.getCountry()))
        .mapToDouble(Account::getBalance)
        .average();
  }

  public static String getFullNames(List<List<Account>> accounts) {
    return accounts.stream()
        .flatMap(List::stream)
        .map(account -> account.getFirstName() + " " + account.getLastName())
        .collect(Collectors.joining(", "));
  }

  public static List<Account> getSortedByName(List<Account> accounts) {
    return accounts.stream()
        .sorted(Comparator.comparing(Account::getLastName)
            .thenComparing(Account::getFirstName))
        .collect(Collectors.toList());
  }


  public static Optional<Account> getOldest(List<Account> accounts) {
    return accounts.stream()
        .min(Comparator.comparing(Account::getBirthday));
  }

  public static Map<Integer, Double> getAverageBalanceByYearOfBirth(List<Account> accounts) {
    return accounts.stream()
        .collect(Collectors.groupingBy(
            account -> account.getBirthday().getYear(),
            Collectors.averagingDouble(Account::getBalance)
        ));
  }


  public static Optional<Account> getLongestLastName(List<Account> accounts) {
    return accounts.stream()
        .max(Comparator.comparingInt(account -> account.getLastName().length()));
  }


}
