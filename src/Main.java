
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
            .birthday(LocalDate.of(1995, 11, 19))
            .balance(60000)
            .gender(Gender.MALE)
            .build());

    List<List<Account>> accountsLists = List.of(accounts);

    AccountService accountService = new AccountService();

    System.out.println(accountService.findExceedingBalance(accounts, 500));
    System.out.println("---------------");
    System.out.println(accountService.findUniqueCountry(accounts));
    System.out.println("---------------");
    System.out.println(accountService.hasYoungerThan(accounts, 2000));
    System.out.println("---------------");
    System.out.println(accountService.findSumBalanceByGender(accounts));
    System.out.println("---------------");
    System.out.println(accountService.groupByMonth(accounts));
    System.out.println("---------------");
    System.out.println(accountService.findAverBalByCountry(accounts, "France"));
    System.out.println("---------------");
    System.out.println(accountService.getFullNames(accountsLists));
    System.out.println("---------------");
    System.out.println(accountService.getSortedByName(accounts));
    System.out.println("---------------");
    System.out.println(accountService.getOldest(accounts));
    System.out.println("---------------");
    System.out.println(accountService.getAverageBalanceByYearOfBirth(accounts));
    System.out.println("---------------");
    System.out.println(accountService.getLongestLastName(accounts));


  }
}