package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.ClientDetails;
import kz.greetgo.sandbox.controller.model.ClientInfo;
import kz.greetgo.sandbox.controller.model.Directory;
import kz.greetgo.sandbox.controller.model.PhoneInfo;

import java.util.List;

public interface ClientRegister {

  List<ClientInfo> getClientList();

  ClientDetails getClientDetails(String id);

  List<Directory> getCharms();

  List<Directory> getGenders();

  List<Directory> getPhoneTypes();

  PhoneInfo getNewPhone(String num, String type);
}