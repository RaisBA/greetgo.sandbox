package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.*;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.AuthRegister;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.security.NoSecurity;
import kz.greetgo.sandbox.controller.util.Controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Bean
@Mapping("/client")
public class ClientController implements Controller {

  public BeanGetter<ClientRegister> clientRegister;

  @ToJson
  @NoSecurity
  @Mapping("/list")
  public List<ClientInfo> getClientList() {
    return clientRegister.get().getClientList();
  }

  @ToJson
  @Mapping("/details")
  public ClientDetails getClientDetails(@Par("clientId") String id){
    return clientRegister.get().getClientDetails(id);
  }

  @ToJson
  @Mapping("/charms")
  public List<Directory> getCharms(){
    return clientRegister.get().getCharms();
  }

  @ToJson
  @Mapping("/genders")
  public List<Directory> getGenders(){
    return clientRegister.get().getGenders();
  }

  @ToJson
  @Mapping("/phoneTypes")
  public List<Directory> getPhoneTypes(){
    return clientRegister.get().getPhoneTypes();
  }
}
