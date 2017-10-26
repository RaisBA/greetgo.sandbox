package kz.greetgo.sandbox.db.test.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface ClientTestDao {
  @Insert("insert into charm (id, name, description, energy, actual)"
    + " values (#{id}, #{name}, #{description}, #{energy}, #{actual})")
  void insertCharm(@Param("id") Integer id, @Param("name") String name, @Param("description") String description,
                   @Param("energy") Float energy, @Param("actual") Boolean actual);

  @Insert("insert into client (id, name, surname, patronymic, gender, birth_date, charm_id, actual)"
    + " values (#{id}, #{name}, #{surname}, #{patronymic}, #{gender}, #{birthDate}, #{charmId}, #{actual})")
  void insertClient(@Param("id") Integer id, @Param("name") String name, @Param("surname") String surname,
                    @Param("patronymic") String patronymic, @Param("gender") String gender,
                    @Param("birthDate") Date birthDate, @Param("charmId") Integer charmId,
                    @Param("actual") Boolean actual);

  @Insert("insert into client_account (id, client_id, money, num, registered_at, actual)"
    + " values (#{id}, #{clientId}, #{money}, #{num}, #{register_at}, #{actual})")
  void insertClientAccount(@Param("id") Integer id, @Param("clientId") Integer clientId,
                           @Param("money") Double money, @Param("num") String num,
                           @Param("register_at") Date registerAt, @Param("actual") Boolean actual);


  @Delete("delete from charm")
  void clearCharm();

  @Delete("delete from client")
  void clearClient();

  @Delete("delete from client_account")
  void clearClientAccount();
}
