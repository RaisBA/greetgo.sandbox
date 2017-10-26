package kz.greetgo.sandbox.db.jdbc;

import kz.greetgo.db.ConnectionCallback;
import kz.greetgo.sandbox.controller.model.ClientInfo;
import kz.greetgo.sandbox.controller.model.SortType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ClientListCallback implements ConnectionCallback<List<ClientInfo>> {

  private String  sortColumn;
  private String  sortDirect;
  private Integer pageNum;
  private Integer pageSize;

  private final StringBuilder sql = new StringBuilder();
  private final List<Object> params = new LinkedList<>();

  public ClientListCallback(Integer sortType, Integer sortDirect, Integer pageSize ,Integer pageNum){

    this.sortColumn = getSortColumn(sortType);
    this.sortDirect = getSortDirect(sortDirect);
    this.pageSize = pageSize;
    this.pageNum = pageNum;
  }

  @Override
  public List<ClientInfo> doInConnection(Connection connection) throws Exception {
    createSql();

    try(PreparedStatement ps = connection.prepareStatement(sql.toString())){

      int i  = 1;
      for (Object o : params) {
        ps.setObject(i++, o);
      }

      try(ResultSet rs = ps.executeQuery();){
        List<ClientInfo> result = new LinkedList<>();

        while (rs.next()) {
          result.add(toInfo(rs));
        }

        return result;
      }

    }

  }

  private ClientInfo toInfo(ResultSet rs) throws SQLException{
    ClientInfo result = new ClientInfo();

    result.id = rs.getString("id");
    result.fullName = rs.getString("fullName");
    result.age = rs.getInt("age");
    result.charm = rs.getString("charm");
    result.totalScore= rs.getDouble("totalScore");
    result.maxScore = rs.getDouble("maxScore");
    result.minScore = rs.getDouble("minScore");

    return result;
  }

  private void appendSelect(){
    sql.append("with money as (");
    sql.append("  select client_id, sum(money) as total_score, max(money) as max_score, min(money) as min_score");
    sql.append("  from client_account group by client_id");
    sql.append(")");

    sql.append("select");
    sql.append(" c.id as id");
    sql.append(", c.surname||' '||c.name||' '||c.patronymic as fullName");
    sql.append(", extract ( YEAR from age(now(), c.birth_date)) as age");
    sql.append(", ch.name as charm");
    sql.append(", m.total_score as totalScore");
    sql.append(", m.max_score as maxScore");
    sql.append(", m.min_score as minScore ");
  }

  private void createSql(){
    appendSelect();

    sql.append("from client c");
    sql.append(" left join charm ch on ch.id=c.charm_id");
    sql.append(" left join money m on m.client_id = c.id");

    sql.append(" where c.actual = true");
    sql.append(" order by ").append(sortColumn).append(" ").append(sortDirect);

    sql.append(" limit ").append(pageSize);
    sql.append(" offset ").append((pageNum - 1) * pageSize);

  }


  private String getSortColumn(Integer sortType) {
    return SortType.getColumnNameTo(sortType);
  }

  private String getSortDirect(Integer sortDirect) {
    if (sortDirect == null || sortDirect > 0) {
      return "asc";
    }

    return "desc";
  }

}
