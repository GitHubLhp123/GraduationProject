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
public class Parameter {

    private String password;
    private List<String> column;
    private List<Connection> connection;
    private String splitPk;
    private String username;
    public void setPassword(String password) {
         this.password = password;
     }
     public String getPassword() {
         return password;
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

    public void setSplitPk(String splitPk) {
         this.splitPk = splitPk;
     }
     public String getSplitPk() {
         return splitPk;
     }

    public void setUsername(String username) {
         this.username = username;
     }
     public String getUsername() {
         return username;
     }

}