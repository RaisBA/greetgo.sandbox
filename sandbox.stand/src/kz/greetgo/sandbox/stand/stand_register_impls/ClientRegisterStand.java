package kz.greetgo.sandbox.stand.stand_register_impls;

import com.sun.org.apache.xpath.internal.operations.Bool;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Bean
public class ClientRegisterStand implements ClientRegister {

  private final static int PAGE_SIZE = 20;
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
      res.gender = "NO";
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
      clients.put("2", c2);
      clients.put("3", c2);
      clients.put("4", c2);
      clients.put("5", c2);
      clients.put("6", c2);
      clients.put("7", c2);
      clients.put("8", c2);
      clients.put("9", c2);
      clients.put("10", c2);
      clients.put("12", c2);
      clients.put("13", c2);
      clients.put("14", c2);
      clients.put("15", c2);
      clients.put("16", c2);
      clients.put("17", c2);
      clients.put("18", c2);
      clients.put("19", c2);
      clients.put("11", c2);
      clients.put("20", c2);
      clients.put("22", c2);
      clients.put("23", c2);
      clients.put("24", c2);
      clients.put("25", c2);
      clients.put("26", c2);
      clients.put("27", c2);
      clients.put("28", c2);
      clients.put("29", c2);
      clients.put("21", c2);
    }
  }

  @Override
  public List<ClientInfo> getClientList(Integer sortType, Integer sortDirect, Integer pageNum) {
    if (sortType == null) {
      return clients.values().stream().skip((pageNum-1) * PAGE_SIZE).limit(PAGE_SIZE).collect(Collectors.toList());
    }

    return clients.values().stream()
      .sorted((t1, t2) -> {
      switch (sortType){
        case 1:
          return  (sortDirect * Integer.compare(t1.age, t2.age));
        case 2:
          return  (sortDirect * Double.compare(t1.totalScore, t2.totalScore));
        case 3:
          return  (sortDirect * Double.compare(t1.maxScore, t2.maxScore));
        case 4:
          return  (sortDirect * Double.compare(t1.minScore, t2.minScore));
        default:
          return 0;
      }
    })
      .skip((pageNum-1) * PAGE_SIZE)
      .limit(PAGE_SIZE)
      .collect(Collectors.toList());
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
    list.add(new Directory("NO", "Все сложно"));

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
  public Boolean saveClient(ClientDetails clientDetails) {
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

    return true;
  }

  @Override
  public PageResultInfo chekPage(int page) {
    PageResultInfo result = new PageResultInfo();
    if (page > 9){
      page = 9;
    }
    result.page = page;
    int i = page - 1;

    if (page == 1){
      i++;
    }

    if (i >  1){
      result.pagesList.add("...");
    }
    for(; i <= page + 1 && i <= 9; i++){
      result.pagesList.add("" + i);
    }

    if (i <= 9){
      result.pagesList.add("...");
    }

    return result;
  }
}
