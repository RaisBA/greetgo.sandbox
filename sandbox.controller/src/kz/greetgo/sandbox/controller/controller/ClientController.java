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
    c1.fullName = "Иванов Иван Иванович";
    c1.age = 12;
    c1.charm = "Спокойный";
    c1.maxScore = 12000;
    c1.minScore = 12000;
    c1.totalScore = 12000;

    list.add(c1);
    return list;
  }
}
