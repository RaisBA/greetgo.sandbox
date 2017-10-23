package kz.greetgo.sandbox.stand.stand_register_impls;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Bean
public class ClientRegisterStand implements ClientRegister {

  private static int phoneId = 124512;
  private static int clientId = 124512;
  private Map<String, List<PhoneInfo>> phonesL = new LinkedHashMap<>();
  private Map<String, ClientInfo> clients = new LinkedHashMap<>();
  private Map<String, ClientDetails> clientDetails = new LinkedHashMap<>();
  private Map<String, String> charms = new LinkedHashMap<>();
  private Map<String, String> genders = new LinkedHashMap<>();

  {
    charms.put("CHARM_1", "Спокойный");
    charms.put("CHARM_2", "50 грамм");
  }

  {
    {
      List<PhoneInfo> phones = new LinkedList<>();
      phones.add(new PhoneInfo("1", "+ 7 777 161 13 23 ", "MOBILE"));
      phones.add(new PhoneInfo("2", "+ 7 2424 461 61 27 ", "HOME"));
      phonesL.put("1", phones);
    }
    {
      List<PhoneInfo> phones = new LinkedList<>();
      phones.add(new PhoneInfo("1", "+ 7 712 125  14 09 ", "MOBILE"));
      phones.add(new PhoneInfo("2", "+ 7 2424 461 61 27 ", "HOME"));
      phonesL.put("2", phones);
    }
  }

  {
    {
      ClientInfo c1 = new ClientInfo();
      c1.id = "1";
      c1.fullName = "Иванов Иван Иванович";
      c1.age = 12;
      c1.charm = "Спокойный";
      c1.maxScore = 12000;
      c1.minScore = 1000;
      c1.totalScore = 1250.65;

      ClientDetails res = new ClientDetails();
      res.id = "1";
      res.name = "Иван";
      res.surname = "Иванов";
      res.patronymic = "Иванович";
      res.birthDate = "2005-09-12";
      res.charm = "CHARM_2";
      res.gender = "NON";
      AddressInfo regAddress = new AddressInfo();
      regAddress.flat = "12";
      regAddress.house = "1";
      regAddress.street = "Лермонтова";
      AddressInfo factAddress = new AddressInfo();
      factAddress.flat = "125";
      factAddress.house = "61";
      factAddress.street = "Пушкина";
      List<PhoneInfo> phones = this.phonesL.get("1");
      res.factAddress = factAddress;
      res.regAddress = regAddress;
      res.phones = phones;

      clientDetails.put("1", res);
      clients.put(c1.id, c1);
    }

    {
      ClientInfo c2 = new ClientInfo();
      c2.id = "2";
      c2.fullName = "Егоров Егор Егорович";
      c2.age = 43;
      c2.charm = "50 грамм";
      c2.maxScore = 61212;
      c2.minScore = 12126;
      c2.totalScore = 1000.112;

      clients.put(c2.id, c2);
      ClientDetails res = new ClientDetails();
      res.id = "2";
      res.name = "Егор";
      res.surname = "Егоров";
      res.patronymic = "Егорович";
      res.birthDate = "2012-09-12";
      res.charm = "CHARM_1";
      res.gender = "MAIL";
      AddressInfo regAddress = new AddressInfo();
      regAddress.flat = "12";
      regAddress.house = "1";
      regAddress.street = "Лермонтова";
      AddressInfo factAddress = new AddressInfo();
      factAddress.flat = "125";
      factAddress.house = "61";
      factAddress.street = "Пушкина";
      List<PhoneInfo> phones = this.phonesL.get("2");
      res.factAddress = factAddress;
      res.regAddress = regAddress;
      res.phones = phones;

      clientDetails.put("2", res);
    }
  }

  @Override
  public List<ClientInfo> getClientList() {
    return new LinkedList<>(clients.values());
  }

  @Override
  public ClientDetails getClientDetails(String id) {
    if (id == null) {
      ClientDetails details = new ClientDetails();
      details.id = ++clientId + "";
      clientDetails.put(details.id, details);
      return details;
    }

    return clientDetails.get(id);
  }

  @Override
  public List<Directory> getCharms() {
    List<Directory> list = new LinkedList<>();

    list.add(new Directory("CHARM_1", "Спокойный"));
    list.add(new Directory("CHARM_2", "50 грамм"));

    return list;
  }

  @Override
  public List<Directory> getGenders() {
    List<Directory> list = new LinkedList<>();

    list.add(new Directory("MALE", "Мужчина"));
    list.add(new Directory("FEMALE", "Женщина"));
    list.add(new Directory("NON", "Все сложно"));

    return list;
  }

  @Override
  public List<Directory> getPhoneTypes() {
    List<Directory> list = new LinkedList<>();

    list.add(new Directory("MOBILE", "Мобильный"));
    list.add(new Directory("HOME", "Домашний"));

    return list;
  }

  @Override
  public PhoneInfo getNewPhone(String clientId, String num, String type) {
    PhoneInfo info = new PhoneInfo("" + ++phoneId, num, type);

    if (!phonesL.containsKey(clientId)) {
      phonesL.put(clientId, new LinkedList<>());
    }
    phonesL.get(clientId).add(info);

    return info;
  }

  @Override
  public List<PhoneInfo> deletePhone(String clientId, final String id, String num) {
    phonesL.put(clientId, phonesL.get(clientId).stream().filter(phoneInfo -> !phoneInfo.id.equals(id)).collect(Collectors.toList()));
    return phonesL.get(clientId);
  }

  @Override
  public void deleteClient(String id) {
    if (clients.containsKey(id)) {
      clients.remove(id);
    }
  }

  @Override
  public void saveClient(ClientDetails clientDetails) {
    this.clientDetails.put(clientDetails.id, clientDetails);

    ClientInfo cI;
    if (clients.containsKey(clientDetails.id)) {
      cI = clients.get(clientDetails.id);
    } else {
      cI = new ClientInfo();
    }
    cI.id = clientDetails.id;
    cI.fullName = clientDetails.surname + " " + clientDetails.name + " " + clientDetails.patronymic;
    cI.charm = charms.get(clientDetails.charm);

    this.clients.put(cI.id, cI);
  }
}
