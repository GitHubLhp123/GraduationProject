/**
  * Copyright 2021 json.cn 
  */
package myproject.exexecuter.plug.mysql.writer;
import java.util.List;

/**
 * Auto-generated: 2021-02-19 10:36:1
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Parameter {

    private List<String> column;
    private List<Connection> connection;
    private String password;
    private String username;
    private List<String>  preSql;

    public List<String> getPreSql() {
        return preSql;
    }

    public void setPreSql(List<String> preSql) {
        this.preSql = preSql;
    }

    public void setColumn(List<String> column) {
         this.column = column;
     }
     public List<String> getColumn() {
         return column;
     }

    public void setConnection(List<Connection> connection) {
         this.connection = connection;
     }
     public List<Connection> getConnection() {
         return connection;
     }

    public void setPassword(String password) {
         this.password = password;
     }
     public String getPassword() {
         return password;
     }

    public void setUsername(String username) {
         this.username = username;
     }
     public String getUsername() {
         return username;
     }

}