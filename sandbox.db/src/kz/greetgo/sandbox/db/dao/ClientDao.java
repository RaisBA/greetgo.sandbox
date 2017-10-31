package kz.greetgo.sandbox.db.dao;

import kz.greetgo.sandbox.controller.model.AddressInfo;
import kz.greetgo.sandbox.controller.model.ClientDetails;
import kz.greetgo.sandbox.controller.model.Directory;
import kz.greetgo.sandbox.controller.model.PhoneInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface ClientDao {
  @Select("select id as code, name from charm where actual = true")
  List<Directory> loadCharms();

  @Select("select id as id, phone_num as num, type from client_phone where client_id = #{client_id} and actual = true")
  List<PhoneInfo> loadPhones(@Param("client_id") Integer clientId);


  @Select("select street, house, flat from client_address"
    + " where client_id = #{client_id} and type = #{type}")
  AddressInfo loadAddress(@Param("client_id") Integer clientId, @Param("type") String type);


  @Select("select id, name, surname, patronymic, gender, charm_id as charm, birth_date as birthDate"
    + " from client where id = #{client_id} and actual = true")
  ClientDetails loadDetails(@Param("client_id") Integer clientId);

  @Select("insert into client (actual) values (false) returning id")
  Integer newClient();

  @Select("select count(*) from client where actual = true")
  Integer clientCount();

  @Select("select id, phone_num as num, type from client_phone"
    + " where client_id = #{client_id} and phone_num = #{num} and actual = #{actual}")
  PhoneInfo getPhone(@Param("client_id") Integer clientId, @Param("num") String num, @Param("actual") Boolean actual);

  @Select("insert into client_phone (client_id, phone_num, type, actual)"
    + " values (#{client_id}, #{phone_num}, #{type}, true) returning id")
  Integer insertPhone(@Param("client_id") Integer clientId, @Param("phone_num") String num, @Param("type") String type);

  @Update("update client_phone set phone_num=#{phone_num}, type=#{type}, actual=true where id=#{id}")
  void updatePhone(@Param("id") Integer id, @Param("phone_num") String num, @Param("type") String type);

  @Update("update client_phone set phone_num=#{num}, actual=false where id=#{id}")
  void deletePhone(@Param("id") Integer id, @Param("num") String num);

  @Update("update client set actual=false where id=#{id}")
  void deleteClient(@Param("id") Integer id);


  @Update("update client set name=#{name}, surname=#{surname}, patronymic=#{patronymic}"
    + ", gender=#{gender}, birth_date=#{birthDate}, charm_id=#{charmId}, actual=true where id=#{id}")
  void updateClient(@Param("id") Integer id, @Param("name") String name, @Param("surname") String surname,
                    @Param("patronymic") String patronymic, @Param("gender") String gender,
                    @Param("birthDate") Date birthDate, @Param("charmId") Integer charmId);

  @Insert("insert into client_address (client_id, type, street, house, flat)"
    + " values (#{client_id}, #{type}, #{street}, #{house}, #{flat})")
  void insertAddress(@Param("client_id") Integer clientId, @Param("type") String type,
                     @Param("street") String street, @Param("house") String house, @Param("flat") String flat);

  @Update("update client_address set street=#{street}, house=#{house}, flat=#{flat}"
    + " where client_id = #{client_id} and type = #{type}")
  void updateAddress(@Param("client_id") Integer clientId, @Param("type") String type,
                     @Param("street") String street, @Param("house") String house, @Param("flat") String flat);
}
