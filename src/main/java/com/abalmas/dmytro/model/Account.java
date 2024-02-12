package com.abalmas.dmytro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.abalmas.dmytro.model.enums.Country;
import com.abalmas.dmytro.model.enums.Gender;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Enumerated(EnumType.STRING)
  private Country country;

  @Column(name = "birthday")
  private LocalDate birthday;

  @Column(name = "balance")
  private double balance;

  @Enumerated(EnumType.STRING)
  private Gender gender;

}
