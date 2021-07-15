/**
  * Copyright 2021 json.cn 
  */
package myproject.exexecuter.plug.mysql.reader;
import java.util.List;

/**
 * Auto-generated: 2021-02-19 12:52:26
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Connection {

    private List<String> jdbcUrl;
    private List<String> table;
    public void setJdbcUrl(List<String> jdbcUrl) {
         this.jdbcUrl = jdbcUrl;
     }
     public List<String> getJdbcUrl() {
         return jdbcUrl;
     }

    public void setTable(List<String> table) {
         this.table = table;
     }
     public List<String> getTable() {
         return table;
     }

}