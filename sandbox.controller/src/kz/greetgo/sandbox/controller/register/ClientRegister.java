package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.*;

import java.util.List;

public interface ClientRegister {

  List<ClientInfo> getClientList(Integer sortType, Integer sortDirect, Integer pageNum);

  ClientDetails getClientDetails(String id);

  List<Directory> getCharms();

  List<Directory> getGenders();

  List<Directory> getPhoneTypes();

  PhoneInfo getNewPhone(String clientId, String num, String type);

  List<PhoneInfo> deletePhone(String clientId, String id, String num);

  void deleteClient(String id);

  void saveClient(ClientDetails clientDetails);

  PageResultInfo chekPage(int page);
}
