
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountService {

  public static List<Account> findAccountsExceedingBalance(List<Account> accounts, int balance) {
    return accounts.stream()
        .filter(account -> account.getBalance() > balance)
        .toList();
  }

  public static Set<String> findUniqueCountryNames(List<Account> accounts) {
    return accounts.stream()
        .map(Account::getCountry)
        .collect(Collectors.toSet());
  }

  public static boolean hasAccountYoungerThan(List<Account> accounts, int date) {
    return accounts.stream()
        .anyMatch(account -> account.getBirthday().getYear() > date);
  }


}
