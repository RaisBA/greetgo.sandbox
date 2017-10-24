package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;

import java.util.List;

@Bean
public class ClientRegisterImpl implements ClientRegister {


  @Override
  public List<ClientInfo> getClientList(Integer sortType, Integer sortDirect, Integer pageNum) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ClientDetails getClientDetails(String id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Directory> getCharms() {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Directory> getGenders() {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Directory> getPhoneTypes() {
    throw new UnsupportedOperationException();
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
}
