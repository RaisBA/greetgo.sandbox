package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.dao.ClientDao;
import kz.greetgo.sandbox.db.jdbc.ClientListCallback;
import kz.greetgo.sandbox.db.util.JdbcSandbox;
import org.fest.util.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    return jdbc.get().execute(new ClientListCallback(sortType, sortDirect, PAGE_SIZE, pageNum));
  }

  @Override
  public ClientDetails getClientDetails(String id) {

    if (id == null) {
      return getNewClientDetails();
    }

    Integer clientId = Integer.parseInt(id);
    ClientDetails result = clientDao.get().loadDetails(clientId);

    result.regAddress = clientDao.get().loadAddress(clientId, AddressTypes.REGISTRATION.name());
    result.factAddress = clientDao.get().loadAddress(clientId, AddressTypes.FACTUAL.name());

    if (result.regAddress == null) {
      result.regAddress = new AddressInfo();
    }

    if (result.factAddress == null) {
      result.factAddress = new AddressInfo();
    }

    result.phones = clientDao.get().loadPhones(clientId);

    if (result.phones == null) {
      result.phones = new ArrayList<>();
    }

    return result;
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

    PhoneInfo phone = clientDao.get().getPhone(Integer.parseInt(clientId), num, true);
    if (phone != null) return null;

    phone = clientDao.get().getPhone(Integer.parseInt(clientId), num, false);

    if (phone != null) {
      clientDao.get().updatePhone(Integer.parseInt(phone.id), num, type);
      phone.type = type;
      return phone;
    }

    Integer id = clientDao.get().insertPhone(Integer.parseInt(clientId), num, type);

    phone = new PhoneInfo(id + "", num, type);

    return phone;
  }

  @Override
  public List<PhoneInfo> deletePhone(String clientId, String id, String num) {
    clientDao.get().deletePhone(Integer.parseInt(id), num);


    return clientDao.get().loadPhones(Integer.parseInt(clientId));
  }

  @Override
  public void deleteClient(String id) {
    clientDao.get().deleteClient(Integer.parseInt(id));
  }

  @Override
  public Boolean saveClient(ClientDetails clientDetails) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date;
    try {
      date = sdf.parse(clientDetails.birthDate);
    } catch (ParseException e) {
      e.printStackTrace();
      date = null;
    }

    clientDao.get().updateClient(Integer.parseInt(clientDetails.id), clientDetails.name, clientDetails.surname,
      clientDetails.patronymic, clientDetails.gender,
      date, (Strings.isNullOrEmpty(clientDetails.charm) ? null : Integer.parseInt(clientDetails.charm)));

    saveAddress(clientDetails.id, clientDetails.factAddress, AddressTypes.FACTUAL);
    saveAddress(clientDetails.id, clientDetails.regAddress, AddressTypes.REGISTRATION);


    for (PhoneInfo phone : clientDetails.phones) {
      clientDao.get().updatePhone(Integer.parseInt(phone.id), phone.num, phone.type);
    }

    return true;
  }

  @Override
  public PageResultInfo chekPage(int page) {
    int minPage = 1;
    int maxPage = getMaxPage();

    page = scope(page, minPage, maxPage);

    PageResultInfo result = new PageResultInfo();
    result.page = page;

    if (page > minPage + 1) {
      result.pagesList.add("...");
    }

    for (int i = page - 1; (i <= page + 1 && i <= maxPage); i++) {

      if (i == 0) continue;

      result.pagesList.add(i + "");
    }

    if (page < maxPage - 1) {
      result.pagesList.add("...");
    }


    return result;
  }

  private int scope(int value, int min, int max) {
    if (value < min) {
      return min;

    } else if (value > max) {
      return max;
    }
    return value;
  }

  private int getMaxPage() {
    int maxPage = (int) Math.ceil(clientDao.get().clientCount() / (double) PAGE_SIZE);

    if (maxPage < 1) {
      maxPage = 1;
    }

    return maxPage;
  }


  private ClientDetails getNewClientDetails() {

    ClientDetails result = new ClientDetails();

    result.id = clientDao.get().newClient().toString();


    return result;
  }

  private void saveAddress(String clientId, AddressInfo address, AddressTypes type) {

    AddressInfo addressInfo = clientDao.get().loadAddress(Integer.parseInt(clientId), type.name());

    if (addressInfo == null) {

      clientDao.get().insertAddress(Integer.parseInt(clientId), type.name(), address.street, address.house, address.flat);

    } else {

      clientDao.get().updateAddress(Integer.parseInt(clientId), type.name(), address.street, address.house, address.flat);

    }

  }

  private Integer nullToZero(Integer integer) {
    return (integer == null ? 0 : integer);
  }

  @SuppressWarnings("unused")
  private static <T> T nullChek(T obj, Class<T> tClass) throws Exception {
    if (obj == null) {
      obj = tClass.newInstance();
    }

    return obj;
  }
}
