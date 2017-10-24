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
  public List<ClientInfo> getClientList(@Par("sort") Integer sortType, @Par("direction") Integer sortDirect,
                                        @Par("pageNum") Integer pageNum) {
    return clientRegister.get().getClientList(sortType, sortDirect, pageNum);
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

  @ToJson
  @Mapping("/newPhone")
  public PhoneInfo getNewPhone(@Par("clientId") String clientId, @Par("num") String num, @Par("type") String type){
    return clientRegister.get().getNewPhone(clientId, num, type);
  }

  @ToJson
  @Mapping("/deletePhone")
  public List<PhoneInfo> deletePhone(@Par("clientId") String clientId, @Par("id") String id, @Par("num") String num){
    return clientRegister.get().deletePhone(clientId, id, num);
  }

  @ToJson
  @Mapping("/save")
  public void saveClient(@Par("client") @Json ClientDetails clientDetails){
    clientRegister.get().saveClient(clientDetails);
  }

  @ToJson
  @Mapping("/delete")
  public void deleteClient(@Par("clientId") String clientId){
    clientRegister.get().deleteClient(clientId);
  }

  @ToJson
  @Mapping("/pages")
  public PageResultInfo pages(@Par("page") Integer page){
    return clientRegister.get().chekPage(page);
  }
}
