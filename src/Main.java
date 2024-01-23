import java.time.LocalDate;

public class Main {

  public static void main(String[] args) {

    System.out.println(new Account("Test", "Test", "Test", LocalDate.now(), 100, Gender.MALE));

    System.out.println(new Account.Builder()
        .country("Test5")
        .lastName("Test1")
        .firstName("Test")
        .birthday(LocalDate.of(2000, 5, 25))
        .balance(1000)
        .gender(Gender.FEMALE)
        .build());

  }
}