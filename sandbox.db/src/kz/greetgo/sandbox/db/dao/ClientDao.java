package kz.greetgo.sandbox.db.dao;

import kz.greetgo.sandbox.controller.model.ClientInfo;
import kz.greetgo.sandbox.controller.model.Directory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ClientDao {
  @Select("select id as code, name from charm")
  List<Directory> loadCharms();

  @Select("with money as ("
    +       " select client_id,"
    +       "  sum(money) as total_score,"
    +       "  max(money) as max_score,"
    +       "  min(money) as min_score"
    +       " from client_account"
    +       " group by client_id"
    +     ")"
    + " select c.id as id, c.surname||' '||c.name||' '||c.patronymic as fullName,"
    + " extract ( YEAR from age(now(), c.birth_date)) as age, ch.name as charm,"
    + " m.total_score as totalScore,"
    + " m.max_score as maxScore,"
    + " m.min_score as minScore"
    + " from client c"
    + " left join charm ch on ch.id=c.charm_id"
    + " left join money m on m.client_id = c.id")
  List<ClientInfo> loadClinets(Integer sortType, Integer sortDirect, Integer pageNum);
}
