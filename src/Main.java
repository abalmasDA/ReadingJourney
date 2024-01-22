
import java.time.LocalDate;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    List<Account> accounts = List.of(new Account.Builder()
            .country("Argentina")
            .lastName("Messi")
            .firstName("Lionel")
            .birthday(LocalDate.of(1987, 7, 20))
            .balance(80000000)
            .gender(Gender.MALE)
            .build(),
        new Account.Builder()
            .country("England")
            .lastName("Henderson")
            .firstName("Gordan")
            .birthday(LocalDate.of(1980, 8, 25))
            .balance(45000)
            .gender(Gender.MALE)
            .build(),
        new Account.Builder()
            .country("USA")
            .lastName("Devise")
            .firstName("Ben")
            .birthday(LocalDate.of(1997, 2, 17))
            .balance(300)
            .gender(Gender.MALE)
            .build(),
        new Account.Builder()
            .country("France")
            .lastName("Bazinet")
            .firstName("Sandra")
            .birthday(LocalDate.of(1995, 7, 23))
            .balance(40000)
            .gender(Gender.FEMALE)
            .build(),
        new Account.Builder()
            .country("France")
            .lastName("Mbappe")
            .firstName("Kilian")
            .birthday(LocalDate.of(2001, 11, 19))
            .balance(1500000)
            .gender(Gender.MALE)
            .build());

    System.out.println(AccountService.findAccountsExceedingBalance(accounts, 500));
    System.out.println(AccountService.findUniqueCountryNames(accounts));
    System.out.println(AccountService.hasAccountYoungerThan(accounts, 2000));


  }
}