package kz.greetgo.sandbox.controller.model;


import java.util.Date;
import java.util.List;

public class ClientDetails {
  public String id;
  public String name;
  public String surname;
  public String patronymic;
  public String gender;
  public String charm;
  public Date birthDate;
  public AddressInfo regAddress;
  public AddressInfo factAddress;
  public List<PhoneInfo> phones;
}
