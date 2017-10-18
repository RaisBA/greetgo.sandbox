package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.*;
import kz.greetgo.sandbox.controller.model.AuthInfo;
import kz.greetgo.sandbox.controller.model.ClientInfo;
import kz.greetgo.sandbox.controller.model.UserInfo;
import kz.greetgo.sandbox.controller.register.AuthRegister;
import kz.greetgo.sandbox.controller.security.NoSecurity;
import kz.greetgo.sandbox.controller.util.Controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Bean
@Mapping("/client")
public class ClientController implements Controller {

  public BeanGetter<AuthRegister> authRegister;

  @ToJson
  @NoSecurity
  @Mapping("/list")
  public List<ClientInfo> login() {
    LinkedList<ClientInfo> list = new LinkedList<>();
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
}
