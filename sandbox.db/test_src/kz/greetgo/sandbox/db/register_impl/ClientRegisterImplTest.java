package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.test.dao.ClientTestDao;
import kz.greetgo.sandbox.db.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Набор автоматизированных тестов для тестирования методов класса {@link ClientRegisterImpl}
 */
@SuppressWarnings("Duplicates")
public class ClientRegisterImplTest extends ParentTestNg {

  public BeanGetter<ClientRegister> clientRegister;
  public BeanGetter<ClientTestDao> clientTestDao;

  @Test
  public void getGender() throws Exception {
    List<Directory> genders = clientRegister.get().getGenders();

    assertThat(genders).isNotNull();
    assertThat(genders).hasSize(Genders.values().length);

    assertThat(genders.get(0).code).isEqualTo(Genders.MALE.name());
    assertThat(genders.get(0).name).isEqualTo(Genders.MALE.getTitle());
    assertThat(genders.get(1).code).isEqualTo(Genders.FEMALE.name());
    assertThat(genders.get(1).name).isEqualTo(Genders.FEMALE.getTitle());
    assertThat(genders.get(2).code).isEqualTo(Genders.NO.name());
    assertThat(genders.get(2).name).isEqualTo(Genders.NO.getTitle());
  }

  @Test
  public void getPhoneTypes() throws Exception {
    List<Directory> genders = clientRegister.get().getPhoneTypes();

    assertThat(genders).isNotNull();
    assertThat(genders).hasSize(PhoneTypes.values().length);

  }

  @Test
  public void getCharms() throws Exception {
    clientTestDao.get().clearAddress();
    clientTestDao.get().clearPhone();
    clientTestDao.get().clearClientAccount();
    clientTestDao.get().clearClient();
    clientTestDao.get().clearCharm();
    int size = 10;

    Map<String, String> in = new HashMap<>();

    for (int i = 0; i < size; i++) {
      String name = RND.str(10);
      in.put(i + "", name);
      clientTestDao.get().insertCharm(i, name, RND.str(10), (float) RND.plusDouble(1000, 2), true);
    }

    List<Directory> out = clientRegister.get().getCharms();

    assertThat(out).hasSize(size);
    for (Directory directory : out) {
      assertThat(directory).isNotNull();
      assertThat(in).containsKey(directory.code);
      assertThat(in).containsValue(directory.name);
      assertThat(in.get(directory.code)).isEqualTo(directory.name);
    }
  }

  @Test
  public void getClientListNoPageNoSort() throws Exception {
    clientTestDao.get().clearAddress();
    clientTestDao.get().clearPhone();
    clientTestDao.get().clearClientAccount();
    clientTestDao.get().clearClient();
    clientTestDao.get().clearCharm();
    int size = 10;
    Directory charm = new Directory();
    charm.name = RND.str(10);
    charm.code = RND.intStr(2);
    clientTestDao.get().insertCharm(Integer.parseInt(charm.code), charm.name,
      RND.str(10), (float) RND.plusDouble(1000, 2), true);

    Map<String, ClientInfo> in = new HashMap<>();

    // Загрузка клиентов
    for (int i = 0; i < size; i++) {
      String name = RND.str(10);
      String surname = RND.str(10);
      String patronymic = RND.str(10);
      Date birthDay = RND.dateYears(-100, 0);
      ClientInfo info = new ClientInfo();
      info.id = i + "";
      info.fullName = surname + " " + name + " " + patronymic;
      info.charm = charm.name;
      info.age = ageBetween(birthDay, new Date());
      in.put(info.id, info);
      clientTestDao.get().insertClient(i, name, surname, patronymic, "MALE",
        birthDay, Integer.parseInt(charm.code), true);
    }

    // Загрузка аккаунтов
    for (int i = 0; i < size * 2; i++) {
      int clientId = RND.plusInt(size);
      double money = RND.plusDouble(100000, 2);

      clientTestDao.get().insertClientAccount(RND.plusInt(1000), clientId, money,
        RND.str(10), RND.dateDays(-10000, 0), true);

      ClientInfo info = in.get(clientId + "");
      info.totalScore += money;
      if (info.maxScore < money) info.maxScore = money;
      if (info.minScore > money || info.minScore == 0) info.minScore = money;

      in.put(clientId + " ", info);
    }

    List<ClientInfo> list = clientRegister.get().getClientList(null, null, null);
    assertThat(list).hasSize(size);
    for (ClientInfo info : list) {
      assertThat(info).isNotNull();

      NumberFormat nf = new DecimalFormat(".");
      ClientInfo inC = in.get(info.id);
      assertThat(info.id).isEqualTo(inC.id);
      assertThat(info.fullName).isEqualTo(inC.fullName);
      assertThat(info.age).isEqualTo(inC.age);
      assertThat(info.charm).isEqualTo(inC.charm);
      assertThat(nf.format(info.maxScore)).isEqualTo(nf.format(inC.maxScore));
      assertThat(nf.format(info.minScore)).isEqualTo(nf.format(inC.minScore));
      assertThat(nf.format(info.totalScore)).isEqualTo(nf.format(inC.totalScore));
    }

  }

  @Test
  public void getClientListPage() throws Exception {
    clientTestDao.get().clearAddress();
    clientTestDao.get().clearPhone();
    clientTestDao.get().clearClientAccount();
    clientTestDao.get().clearClient();
    clientTestDao.get().clearCharm();
    int size = 30;
    Directory charm = new Directory();
    charm.name = RND.str(10);
    charm.code = RND.intStr(2);
    clientTestDao.get().insertCharm(Integer.parseInt(charm.code), charm.name,
      RND.str(10), (float) RND.plusDouble(1000, 2), true);

    List<String> ids = new ArrayList<>();

    // Загрузка клиентов
    for (int i = 0; i < size; i++) {
      String name = RND.str(10);
      String surname = RND.str(10);
      String patronymic = RND.str(10);
      Date birthDay = RND.dateYears(-100, 0);
      ids.add(i + "");
      clientTestDao.get().insertClient(i, name, surname, patronymic, "MALE",
        birthDay, Integer.parseInt(charm.code), true);
    }

    {
      List<ClientInfo> list = clientRegister.get().getClientList(null, null, 1);
      assertThat(list).hasSize(20);
      assertThat(list.get(0).id).isEqualTo(ids.get(0));
      assertThat(list.get(19).id).isEqualTo(ids.get(19));
    }
    {
      List<ClientInfo> list = clientRegister.get().getClientList(null, null, 2);
      assertThat(list).hasSize(size - 20);
      assertThat(list.get(0).id).isEqualTo(ids.get(20));
      assertThat(list.get(list.size() - 1).id).isEqualTo(ids.get(size - 1));
    }

    {
      List<ClientInfo> list = clientRegister.get().getClientList(null, null, 2);
      assertThat(list).hasSize(size - 20);
      assertThat(list.get(0).id).isEqualTo(ids.get(20));
      assertThat(list.get(list.size() - 1).id).isEqualTo(ids.get(size - 1));
    }
  }

  @Test
  public void getClientListNoSort() throws Exception {
    clientTestDao.get().clearAddress();
    clientTestDao.get().clearPhone();
    clientTestDao.get().clearClientAccount();
    clientTestDao.get().clearClient();
    clientTestDao.get().clearCharm();
    int size = 30;
    Directory charm = new Directory();
    charm.name = RND.str(10);
    charm.code = RND.intStr(2);
    clientTestDao.get().insertCharm(Integer.parseInt(charm.code), charm.name,
      RND.str(10), (float) RND.plusDouble(1000, 2), true);

    // Загрузка клиентов
    for (int i = 0; i < size; i++) {
      String name = RND.str(10);
      String surname = RND.str(10);
      String patronymic = RND.str(10);
      Date birthDay = RND.dateYears(-100, 0);
      ClientInfo info = new ClientInfo();
      info.id = i + "";
      info.fullName = surname + " " + name + " " + patronymic;
      info.charm = charm.name;
      info.age = ageBetween(birthDay, new Date());
      clientTestDao.get().insertClient(i, name, surname, patronymic, "MALE",
        birthDay, Integer.parseInt(charm.code), true);
    }

    // Загрузка аккаунтов
    for (int i = 0; i < size * 2; i++) {
      int clientId = RND.plusInt(size);
      double money = RND.plusDouble(100000, 2);

      clientTestDao.get().insertClientAccount(RND.plusInt(1000), clientId, money,
        RND.str(10), RND.dateDays(-10000, 0), true);
    }

    List<ClientInfo> list = clientRegister.get().getClientList(89, 1, 1);
    assertThat(list).hasSize(20);

    //noinspection ResultOfMethodCallIgnored
    list.stream().sorted((t1, t2) -> {

      int id1 = Integer.parseInt(t1.id);
      int id2 = Integer.parseInt(t2.id);
      assertThat(Integer.compare(id2, id1)).isLessThanOrEqualTo(0);

      return 0;
    }).collect(Collectors.toList());
  }

  @Test
  public void getClientListAgeSort() throws Exception {
    clientTestDao.get().clearAddress();
    clientTestDao.get().clearPhone();
    clientTestDao.get().clearClientAccount();
    clientTestDao.get().clearClient();
    clientTestDao.get().clearCharm();
    int size = 30;
    Directory charm = new Directory();
    charm.name = RND.str(10);
    charm.code = RND.intStr(2);
    clientTestDao.get().insertCharm(Integer.parseInt(charm.code), charm.name,
      RND.str(10), (float) RND.plusDouble(1000, 2), true);

    // Загрузка клиентов
    for (int i = 0; i < size; i++) {
      String name = RND.str(10);
      String surname = RND.str(10);
      String patronymic = RND.str(10);
      Date birthDay = RND.dateYears(-100, 0);
      ClientInfo info = new ClientInfo();
      info.id = i + "";
      info.fullName = surname + " " + name + " " + patronymic;
      info.charm = charm.name;
      info.age = ageBetween(birthDay, new Date());
      clientTestDao.get().insertClient(i, name, surname, patronymic, "MALE",
        birthDay, Integer.parseInt(charm.code), true);
    }

    // Загрузка аккаунтов
    for (int i = 0; i < size * 2; i++) {
      int clientId = RND.plusInt(size);
      double money = RND.plusDouble(100000, 2);

      clientTestDao.get().insertClientAccount(RND.plusInt(1000000), clientId, money,
        RND.str(10), RND.dateDays(-10000, 0), true);

    }

    {
      List<ClientInfo> list = clientRegister.get().getClientList(1, 1, 1);
      assertThat(list).hasSize(20);

      //noinspection ResultOfMethodCallIgnored
      list.stream().sorted((t1, t2) -> {

        assertThat(Integer.compare(t2.age, t1.age)).isLessThanOrEqualTo(0);

        return 0;
      }).collect(Collectors.toList());
    }

    {
      List<ClientInfo> list = clientRegister.get().getClientList(1, -1, 1);
      assertThat(list).hasSize(20);

      //noinspection ResultOfMethodCallIgnored
      list.stream().sorted((t1, t2) -> {

        assertThat(Integer.compare(t2.age, t1.age)).isGreaterThanOrEqualTo(0);

        return 0;
      }).collect(Collectors.toList());
    }
  }

  @Test
  public void getClientListTotalScoreSort() throws Exception {
    clientTestDao.get().clearAddress();
    clientTestDao.get().clearPhone();
    clientTestDao.get().clearClientAccount();
    clientTestDao.get().clearClient();
    clientTestDao.get().clearCharm();
    int size = 30;
    Directory charm = new Directory();
    charm.name = RND.str(10);
    charm.code = RND.intStr(2);
    clientTestDao.get().insertCharm(Integer.parseInt(charm.code), charm.name,
      RND.str(10), (float) RND.plusDouble(1000, 2), true);


    // Загрузка клиентов
    for (int i = 0; i < size; i++) {
      String name = RND.str(10);
      String surname = RND.str(10);
      String patronymic = RND.str(10);
      Date birthDay = RND.dateYears(-100, 0);
      ClientInfo info = new ClientInfo();
      info.id = i + "";
      info.fullName = surname + " " + name + " " + patronymic;
      info.charm = charm.name;
      info.age = ageBetween(birthDay, new Date());
      clientTestDao.get().insertClient(i, name, surname, patronymic, "MALE",
        birthDay, Integer.parseInt(charm.code), true);
    }

    // Загрузка аккаунтов
    for (int i = 0; i < size * 2; i++) {
      int clientId = RND.plusInt(size);
      double money = RND.plusDouble(100000, 2);

      clientTestDao.get().insertClientAccount(RND.plusInt(1000000), clientId, money,
        RND.str(10), RND.dateDays(-10000, 0), true);

    }

    {
      List<ClientInfo> list = clientRegister.get().getClientList(2, 1, 1);
      assertThat(list).hasSize(20);

      //noinspection ResultOfMethodCallIgnored
      list.stream().sorted((t1, t2) -> {

        assertThat(Double.compare(t2.totalScore, t1.totalScore)).isLessThanOrEqualTo(0);

        return 0;
      }).collect(Collectors.toList());
    }

    {
      List<ClientInfo> list = clientRegister.get().getClientList(2, -1, 1);
      assertThat(list).hasSize(20);

      //noinspection ResultOfMethodCallIgnored
      list.stream().sorted((t1, t2) -> {

        assertThat(Double.compare(t2.totalScore, t1.totalScore)).isGreaterThanOrEqualTo(0);

        return 0;
      }).collect(Collectors.toList());
    }
  }

  @Test
  public void getClientListMaxScoreSort() throws Exception {
    clientTestDao.get().clearAddress();
    clientTestDao.get().clearPhone();
    clientTestDao.get().clearClientAccount();
    clientTestDao.get().clearClient();
    clientTestDao.get().clearCharm();
    int size = 30;
    Directory charm = new Directory();
    charm.name = RND.str(10);
    charm.code = RND.intStr(2);
    clientTestDao.get().insertCharm(Integer.parseInt(charm.code), charm.name,
      RND.str(10), (float) RND.plusDouble(1000, 2), true);

    // Загрузка клиентов
    for (int i = 0; i < size; i++) {
      String name = RND.str(10);
      String surname = RND.str(10);
      String patronymic = RND.str(10);
      Date birthDay = RND.dateYears(-100, 0);
      ClientInfo info = new ClientInfo();
      info.id = i + "";
      info.fullName = surname + " " + name + " " + patronymic;
      info.charm = charm.name;
      info.age = ageBetween(birthDay, new Date());
      clientTestDao.get().insertClient(i, name, surname, patronymic, "MALE",
        birthDay, Integer.parseInt(charm.code), true);
    }

    // Загрузка аккаунтов
    for (int i = 0; i < size * 2; i++) {
      int clientId = RND.plusInt(size);
      double money = RND.plusDouble(100000, 2);

      clientTestDao.get().insertClientAccount(RND.plusInt(1000000), clientId, money,
        RND.str(10), RND.dateDays(-10000, 0), true);
    }

    {
      List<ClientInfo> list = clientRegister.get().getClientList(3, 1, 1);
      assertThat(list).hasSize(20);

      //noinspection ResultOfMethodCallIgnored
      list.stream().sorted((t1, t2) -> {

        assertThat(Double.compare(t2.maxScore, t1.maxScore)).isLessThanOrEqualTo(0);

        return 0;
      }).collect(Collectors.toList());
    }

    {
      List<ClientInfo> list = clientRegister.get().getClientList(3, -1, 1);
      assertThat(list).hasSize(20);

      //noinspection ResultOfMethodCallIgnored
      list.stream().sorted((t1, t2) -> {

        assertThat(Double.compare(t2.maxScore, t1.maxScore)).isGreaterThanOrEqualTo(0);

        return 0;
      }).collect(Collectors.toList());
    }
  }

  @Test
  public void getClientListMinScoreSort() throws Exception {
    clientTestDao.get().clearAddress();
    clientTestDao.get().clearPhone();
    clientTestDao.get().clearClientAccount();
    clientTestDao.get().clearClient();
    clientTestDao.get().clearCharm();
    int size = 30;
    Directory charm = new Directory();
    charm.name = RND.str(10);
    charm.code = RND.intStr(2);
    clientTestDao.get().insertCharm(Integer.parseInt(charm.code), charm.name,
      RND.str(10), (float) RND.plusDouble(1000, 2), true);

    // Загрузка клиентов
    for (int i = 0; i < size; i++) {
      String name = RND.str(10);
      String surname = RND.str(10);
      String patronymic = RND.str(10);
      Date birthDay = RND.dateYears(-100, 0);
      ClientInfo info = new ClientInfo();
      info.id = i + "";
      info.fullName = surname + " " + name + " " + patronymic;
      info.charm = charm.name;
      info.age = ageBetween(birthDay, new Date());
      clientTestDao.get().insertClient(i, name, surname, patronymic, "MALE",
        birthDay, Integer.parseInt(charm.code), true);
    }

    // Загрузка аккаунтов
    for (int i = 0; i < size * 2; i++) {
      int clientId = RND.plusInt(size);
      double money = RND.plusDouble(100000, 2);

      clientTestDao.get().insertClientAccount(RND.plusInt(1000000), clientId, money,
        RND.str(10), RND.dateDays(-10000, 0), true);
    }

    {
      List<ClientInfo> list = clientRegister.get().getClientList(4, 1, 1);
      assertThat(list).hasSize(20);

      //noinspection ResultOfMethodCallIgnored
      list.stream().sorted((t1, t2) -> {

        assertThat(Double.compare(t2.minScore, t1.minScore)).isLessThanOrEqualTo(0);

        return 0;
      }).collect(Collectors.toList());
    }

    {
      List<ClientInfo> list = clientRegister.get().getClientList(4, -1, 1);
      assertThat(list).hasSize(20);

      //noinspection ResultOfMethodCallIgnored
      list.stream().sorted((t1, t2) -> {

        assertThat(Double.compare(t2.minScore, t1.minScore)).isGreaterThanOrEqualTo(0);

        return 0;
      }).collect(Collectors.toList());
    }
  }

  @Test
  public void getClientDetails() {
    clientTestDao.get().clearAddress();
    clientTestDao.get().clearPhone();
    clientTestDao.get().clearClientAccount();
    clientTestDao.get().clearClient();
    clientTestDao.get().clearCharm();
    Directory charm = new Directory();
    charm.name = RND.str(10);
    charm.code = RND.intStr(2);
    clientTestDao.get().insertCharm(Integer.parseInt(charm.code), charm.name,
      RND.str(10), (float) RND.plusDouble(1000, 2), true);

    String name = RND.str(10);
    String surname = RND.str(10);
    String patronymic = RND.str(10);
    String gender = "MALE";
    Date birthDay = RND.dateYears(-100, 0);
    ClientInfo info = new ClientInfo();
    int i = RND.plusInt(100);
    info.id = i + "";
    info.fullName = surname + " " + name + " " + patronymic;
    info.charm = charm.name;
    info.age = ageBetween(birthDay, new Date());

    clientTestDao.get().insertClient(i, name, surname, patronymic, gender,
      birthDay, Integer.parseInt(charm.code), true);

    AddressInfo regAddr = new AddressInfo();
    regAddr.street = RND.str(10);
    regAddr.house = RND.str(10);
    regAddr.flat = RND.str(10);
    clientTestDao.get().insertClientAddress(i, AddressTypes.REGISTRATION.name(), regAddr.street,
      regAddr.house, regAddr.flat, true);

    AddressInfo factAddr = new AddressInfo();
    factAddr.street = RND.str(10);
    factAddr.house = RND.str(10);
    factAddr.flat = RND.str(10);
    clientTestDao.get().insertClientAddress(i, AddressTypes.FACTUAL.name(), factAddr.street,
      factAddr.house, factAddr.flat, true);

    Map<String, PhoneInfo> phoneInfo = new HashMap<>();

    for (int j = 0, n = PhoneTypes.values().length; j < n; j++) {
      PhoneInfo p = new PhoneInfo();
      p.id = j + "";
      p.num = RND.intStr(12);
      p.type = PhoneTypes.values()[j].name();
      clientTestDao.get().insertClientPhone(Integer.parseInt(p.id), i, p.num, p.type, true);
      phoneInfo.put(p.id, p);
    }


    ClientDetails details = clientRegister.get().getClientDetails(i + "");

    assertThat(details).isNotNull();
    assertThat(details.id).isEqualTo(info.id);
    assertThat(details.name).isEqualTo(name);
    assertThat(details.surname).isEqualTo(surname);
    assertThat(details.patronymic).isEqualTo(patronymic);
    assertThat(details.gender).isEqualTo(gender);
    assertThat(details.charm).isEqualTo(charm.code);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    assertThat(details.birthDate).isEqualTo(sdf.format(birthDay));


    assertThat(details.regAddress.street).isEqualTo(regAddr.street);
    assertThat(details.regAddress.house).isEqualTo(regAddr.house);
    assertThat(details.regAddress.flat).isEqualTo(regAddr.flat);

    assertThat(details.factAddress.street).isEqualTo(factAddr.street);
    assertThat(details.factAddress.house).isEqualTo(factAddr.house);
    assertThat(details.factAddress.flat).isEqualTo(factAddr.flat);

    for (PhoneInfo phone : details.phones) {
      assertThat(phoneInfo).containsKey(phone.id);
      PhoneInfo info1 = phoneInfo.get(phone.id);
      assertThat(phone.num).isEqualTo(info1.num);
      assertThat(phone.type).isEqualTo(info1.type);
    }
  }

  @Test
  public void getClientDetailsNewClient() {
    ClientDetails details = clientRegister.get().getClientDetails(null);

    assertThat(details).isNotNull();
    assertThat(details.id).isNotEmpty();
    assertThat(details.phones).isEmpty();
  }

  @Test
  public void chekPage() {
    clientTestDao.get().clearAddress();
    clientTestDao.get().clearPhone();
    clientTestDao.get().clearClientAccount();
    clientTestDao.get().clearClient();
    clientTestDao.get().clearCharm();

    for (int i = 0; i < 86; i++) {
      clientTestDao.get().insertEmptyClient(true);
    }

    {
      PageResultInfo result = clientRegister.get().chekPage(1);
      assertThat(result.page).isEqualTo(1);
      assertThat(result.pagesList).hasSize(3);
      assertThat(result.pagesList.get(0)).isEqualTo("1");
      assertThat(result.pagesList.get(1)).isEqualTo("2");
      assertThat(result.pagesList.get(2)).isEqualTo("...");
    }

    {
      PageResultInfo result = clientRegister.get().chekPage(2);
      assertThat(result.page).isEqualTo(2);
      assertThat(result.pagesList).hasSize(4);
      assertThat(result.pagesList.get(0)).isEqualTo("1");
      assertThat(result.pagesList.get(1)).isEqualTo("2");
      assertThat(result.pagesList.get(2)).isEqualTo("3");
      assertThat(result.pagesList.get(3)).isEqualTo("...");
    }

    {
      PageResultInfo result = clientRegister.get().chekPage(3);
      assertThat(result.page).isEqualTo(3);
      assertThat(result.pagesList).hasSize(5);
      assertThat(result.pagesList.get(0)).isEqualTo("...");
      assertThat(result.pagesList.get(1)).isEqualTo("2");
      assertThat(result.pagesList.get(2)).isEqualTo("3");
      assertThat(result.pagesList.get(3)).isEqualTo("4");
      assertThat(result.pagesList.get(4)).isEqualTo("...");
    }

    {
      PageResultInfo result = clientRegister.get().chekPage(4);
      assertThat(result.page).isEqualTo(4);
      assertThat(result.pagesList).hasSize(4);
      assertThat(result.pagesList.get(0)).isEqualTo("...");
      assertThat(result.pagesList.get(1)).isEqualTo("3");
      assertThat(result.pagesList.get(2)).isEqualTo("4");
      assertThat(result.pagesList.get(3)).isEqualTo("5");
    }

    {
      PageResultInfo result = clientRegister.get().chekPage(5);
      assertThat(result.page).isEqualTo(5);
      assertThat(result.pagesList).hasSize(3);
      assertThat(result.pagesList.get(0)).isEqualTo("...");
      assertThat(result.pagesList.get(1)).isEqualTo("4");
      assertThat(result.pagesList.get(2)).isEqualTo("5");
    }

    {
      PageResultInfo result = clientRegister.get().chekPage(6);
      assertThat(result.page).isEqualTo(5);
      assertThat(result.pagesList).hasSize(3);
      assertThat(result.pagesList.get(0)).isEqualTo("...");
      assertThat(result.pagesList.get(1)).isEqualTo("4");
      assertThat(result.pagesList.get(2)).isEqualTo("5");
    }
  }


  @Test
  public void getNewPhone() {
    clientTestDao.get().clearAddress();
    clientTestDao.get().clearPhone();
    clientTestDao.get().clearClientAccount();
    clientTestDao.get().clearClient();
    clientTestDao.get().clearCharm();


    int clientId = RND.plusInt(100);
    clientTestDao.get().insertClient(clientId, null, null, null, null,
      null, null, true);

    String num = RND.intStr(12);
    {
      PhoneInfo phone = clientRegister.get().getNewPhone(clientId + "", num, PhoneTypes.HOME.name());

      assertThat(phone).isNotNull();
      assertThat(phone.id).isNotNull();

      assertThat(phone.num).isEqualTo(num);
      assertThat(phone.type).isEqualTo(PhoneTypes.HOME.name());
    }
    {
      PhoneInfo phone = clientRegister.get().getNewPhone(clientId + "", num, PhoneTypes.HOME.name());

      assertThat(phone).isNull();
    }
  }

  @Test
  public void getNewPhoneHasDeactual() {
    clientTestDao.get().clearAddress();
    clientTestDao.get().clearPhone();
    clientTestDao.get().clearClientAccount();
    clientTestDao.get().clearClient();
    clientTestDao.get().clearCharm();

    int clientId = RND.plusInt(100);
    clientTestDao.get().insertClient(clientId, null, null, null, null,
      null, null, true);

    String num = RND.intStr(12);

    clientTestDao.get().insertClientPhone(123, clientId, num, PhoneTypes.HOME.name(), false);

    PhoneInfo phone = clientRegister.get().getNewPhone(clientId + "", num, PhoneTypes.MOBILE.name());

    assertThat(phone.id).isEqualTo(123 + "");
    assertThat(phone.type).isEqualTo(PhoneTypes.MOBILE.name());

  }

  @Test
  public void deletePhone() {
    clientTestDao.get().clearAddress();
    clientTestDao.get().clearPhone();
    clientTestDao.get().clearClientAccount();
    clientTestDao.get().clearClient();
    clientTestDao.get().clearCharm();

    int clientId = RND.plusInt(100);
    clientTestDao.get().insertClient(clientId, null, null, null, null,
      null, null, true);
    String num = RND.intStr(12);
    int phoneId = RND.plusInt(125);
    clientTestDao.get().insertClientPhone(phoneId, clientId, num, PhoneTypes.HOME.name(), false);

    List<PhoneInfo> infos = clientRegister.get().deletePhone(clientId + "", phoneId + "", num);

    assertThat(infos).isEmpty();
  }


  @Test
  public void saveClient() {
    clientTestDao.get().clearAddress();
    clientTestDao.get().clearPhone();
    clientTestDao.get().clearClientAccount();
    clientTestDao.get().clearClient();
    clientTestDao.get().clearCharm();
    Directory charm = new Directory();
    charm.name = RND.str(10);
    charm.code = RND.intStr(2);
    clientTestDao.get().insertCharm(Integer.parseInt(charm.code), charm.name,
      RND.str(10), (float) RND.plusDouble(1000, 2), true);

    ClientDetails details = clientRegister.get().getClientDetails(null);

    details.name = RND.str(10);
    details.surname = RND.str(10);
    details.patronymic = RND.str(10);
    details.gender = "MALE";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    details.birthDate = sdf.format(RND.dateYears(-100, 0));
    details.charm = charm.code;

    details.regAddress = new AddressInfo();
    details.regAddress.street = RND.str(10);
    details.regAddress.house = RND.str(10);
    details.regAddress.flat = RND.str(10);

    details.factAddress = new AddressInfo();
    details.factAddress.street = RND.str(10);
    details.factAddress.house = RND.str(10);
    details.factAddress.flat = RND.str(10);

    Map<String, PhoneInfo> phoneInfo = new HashMap<>();

    for (int j = 0, n = PhoneTypes.values().length; j < n; j++) {
      PhoneInfo p = new PhoneInfo();
      p.id = j + "";
      p.num = RND.intStr(12);
      p.type = PhoneTypes.values()[j].name();
      phoneInfo.put(p.id, p);
      details.phones.add(p);
      clientTestDao.get().insertClientPhone(Integer.parseInt(p.id), Integer.parseInt(details.id), p.num, p.type, true);
    }


    clientRegister.get().saveClient(details);

    ClientDetails details1 = clientRegister.get().getClientDetails(details.id);

    assertThat(details1).isNotNull();
    assertThat(details1.id).isEqualTo(details.id);
    assertThat(details1.name).isEqualTo(details.name);
    assertThat(details1.surname).isEqualTo(details.surname);
    assertThat(details1.patronymic).isEqualTo(details.patronymic);
    assertThat(details1.gender).isEqualTo(details.gender);
    assertThat(details1.charm).isEqualTo(charm.code);

    assertThat(details1.birthDate).isEqualTo(details.birthDate);


    assertThat(details1.regAddress.street).isEqualTo(details.regAddress.street);
    assertThat(details1.regAddress.house).isEqualTo(details.regAddress.house);
    assertThat(details1.regAddress.flat).isEqualTo(details.regAddress.flat);

    assertThat(details1.factAddress.street).isEqualTo(details.factAddress.street);
    assertThat(details1.factAddress.house).isEqualTo(details.factAddress.house);
    assertThat(details1.factAddress.flat).isEqualTo(details.factAddress.flat);

    assertThat(details1.phones).hasSize(details.phones.size());

    for (PhoneInfo phone : details1.phones) {
      assertThat(phoneInfo).containsKey(phone.id);
      PhoneInfo info1 = phoneInfo.get(phone.id);
      assertThat(phone.num).isEqualTo(info1.num);
      assertThat(phone.type).isEqualTo(info1.type);
    }
  }


  private int ageBetween(Date from, Date to) {
    GregorianCalendar start = new GregorianCalendar();
    GregorianCalendar end = new GregorianCalendar();

    end.setTime(from);
    start.setTime(to);

    int age = start.get(Calendar.YEAR) - end.get(Calendar.YEAR);
    if (start.get(Calendar.MONTH) < end.get(Calendar.MONTH)) age--;
    else if (start.get(Calendar.MONTH) == end.get(Calendar.MONTH)
      && start.get(Calendar.DATE) < end.get(Calendar.DATE)) age--;

    return age;
  }
}