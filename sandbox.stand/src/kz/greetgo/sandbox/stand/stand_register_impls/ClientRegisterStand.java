package kz.greetgo.sandbox.stand.stand_register_impls;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.errors.AuthError;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.AuthRegister;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.register.model.SessionInfo;
import kz.greetgo.sandbox.controller.register.model.UserParamName;
import kz.greetgo.sandbox.controller.security.SecurityError;
import kz.greetgo.sandbox.db.stand.beans.StandDb;
import kz.greetgo.sandbox.db.stand.model.PersonDot;
import kz.greetgo.util.ServerUtil;
import org.apache.cxf.endpoint.Client;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Bean
public class ClientRegisterStand implements ClientRegister {
  @Override
  public List<ClientInfo> getClientList() {
    List<ClientInfo> list = new LinkedList<>();

    ClientInfo c1 = new ClientInfo();
    c1.id = "1";
    c1.fullName = "Иванов Иван Иванович";
    c1.age = 12;
    c1.charm = "Спокойный";
    c1.maxScore = 12000;
    c1.minScore = 1000;
    c1.totalScore = 1250.65;


    ClientInfo c2 = new ClientInfo();
    c2.id = "2";
    c2.fullName = "Егоров Егор Егорович";
    c2.age = 43;
    c2.charm = "50 грамм";
    c2.maxScore = 61212;
    c2.minScore = 12126;
    c2.totalScore = 1000.112;

    list.add(c1);
    list.add(c2);

    return list;
  }

  @Override
  public ClientDetails getClientDetails(String id) {
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
    List<PhoneInfo> phones = new LinkedList<>();
    phones.add(new PhoneInfo("+ 7 777 161 13 23 ", "MOBILE"));
    phones.add(new PhoneInfo("+ 7 2424 461 61 27 ", "HOME"));

    res.factAddress = factAddress;
    res.regAddress = regAddress;
    res.phones = phones;

    return res;
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
}
