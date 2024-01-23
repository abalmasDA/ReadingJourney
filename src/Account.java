
import java.time.LocalDate;
import java.util.Objects;

public class Account {

  private String firstName;
  private String lastName;
  private String country;
  private LocalDate birthday;
  private int balance;
  private Gender gender;


  public Account() {
  }

  public Account(String firstName, String lastName, String country, LocalDate birthday, int balance,
      Gender gender) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.country = country;
    this.birthday = birthday;
    this.balance = balance;
    this.gender = gender;
  }

  private Account(Builder builder) {
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
    this.country = builder.country;
    this.birthday = builder.birthday;
    this.balance = builder.balance;
    this.gender = builder.gender;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  public int getBalance() {
    return balance;
  }

  public void setBalance(int balance) {
    this.balance = balance;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return balance == account.balance && Objects.equals(firstName, account.firstName)
        && Objects.equals(lastName, account.lastName) && Objects.equals(country,
        account.country) && Objects.equals(birthday, account.birthday)
        && gender == account.gender;
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, country, birthday, balance, gender);
  }

  @Override
  public String toString() {
    return "Account{" +
        "firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", country='" + country + '\'' +
        ", birthday=" + birthday +
        ", balance=" + balance +
        ", gender=" + gender.getValue() +
        '}';
  }

  public static class Builder {

    private String firstName;
    private String lastName;
    private String country;
    private LocalDate birthday;
    private int balance;
    private Gender gender;

    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder country(String country) {
      this.country = country;
      return this;
    }

    public Builder birthday(LocalDate birthday) {
      this.birthday = birthday;
      return this;
    }

    public Builder balance(int balance) {
      this.balance = balance;
      return this;
    }

    public Builder gender(Gender gender) {
      this.gender = gender;
      return this;
    }

    public Account build() {
      return new Account(this);
    }
  }

}
