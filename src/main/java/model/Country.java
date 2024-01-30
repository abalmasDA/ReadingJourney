package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Country {

  ARGENTINA("Argentina"),
  ENGLAND("England"),
  FRANCE("France"),
  USA("USA"),
  GERMANY("Germany"),
  UKRAINE("Ukraine");

  private final String value;

}
