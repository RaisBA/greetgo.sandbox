package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.db.ConnectionCallback;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.dao.ClientDao;
import kz.greetgo.sandbox.db.jdbc.ClientListCallback;
import kz.greetgo.sandbox.db.util.JdbcSandbox;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static kz.greetgo.sandbox.controller.model.Genders.FEMALE;
import static kz.greetgo.sandbox.controller.model.Genders.MALE;
import static kz.greetgo.sandbox.controller.model.Genders.NO;

@Bean
public class ClientRegisterImpl implements ClientRegister {

  public BeanGetter<ClientDao> clientDao;
  public BeanGetter<JdbcSandbox> jdbc;

  private final static int PAGE_SIZE = 20;


  @Override
  public List<ClientInfo> getClientList(Integer sortType, Integer sortDirect, Integer pageNum) {
    sortType = nullToZero(sortType);
    sortDirect = nullToZero(sortDirect);
    pageNum = nullToZero(pageNum);


    return jdbc.get().execute(new ClientListCallback(sortType, sortDirect, PAGE_SIZE ,pageNum));
  }

  @Override
  public ClientDetails getClientDetails(String id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Directory> getCharms() {
    return clientDao.get().loadCharms();
  }

  @Override
  public List<Directory> getGenders() {

    List<Directory> res = new ArrayList<>(Genders.values().length);

    for (Genders gender : Genders.values()) {
      res.add(new Directory(gender.name(), gender.getTitle()));
    }

    return res;
  }

  @Override
  public List<Directory> getPhoneTypes() {
    List<Directory> res = new ArrayList<>(PhoneTypes.values().length);

    for (PhoneTypes phone : PhoneTypes.values()) {
      res.add(new Directory(phone.name(), phone.getTitle()));
    }

    return res;
  }

  @Override
  public PhoneInfo getNewPhone(String clientId, String num, String type) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<PhoneInfo> deletePhone(String clientId, String id, String num) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void deleteClient(String id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void saveClient(ClientDetails clientDetails) {
    throw new UnsupportedOperationException();
  }

  @Override
  public PageResultInfo chekPage(int page) {
    throw new UnsupportedOperationException();
  }


  private Integer nullToZero(Integer integer) {
    if(integer == null){
      return 0;
    }

    return (integer == null ? 0 : integer);
  }
}
