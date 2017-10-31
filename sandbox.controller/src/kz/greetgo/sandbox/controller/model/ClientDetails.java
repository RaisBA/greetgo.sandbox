package kz.greetgo.sandbox.controller.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientDetails {
  public String id;
  public String name;
  public String surname;
  public String patronymic;
  public String gender;
  public String charm;
  public String  birthDate;
  public AddressInfo regAddress = new AddressInfo();
  public AddressInfo factAddress = new AddressInfo();
  public List<PhoneInfo> phones = new ArrayList<>();

  @Override
  public String toString() {
    return "ClientDetails{" +
      "id='" + id + '\'' +
      ", name='" + name + '\'' +
      ", surname='" + surname + '\'' +
      ", patronymic='" + patronymic + '\'' +
      ", gender='" + gender + '\'' +
      ", charm='" + charm + '\'' +
      ", birthDate='" + birthDate + '\'' +
      ", regAddress=" + regAddress +
      ", factAddress=" + factAddress +
      ", phones=" + phones +
      '}';
  }
}
