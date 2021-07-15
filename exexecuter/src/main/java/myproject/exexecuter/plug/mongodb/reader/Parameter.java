/**
  * Copyright 2021 json.cn 
  */
package myproject.exexecuter.plug.mongodb.reader;
import java.util.List;

/**
 * Auto-generated: 2021-03-03 22:15:57
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Parameter {

    private String userName;
    private String userPassword;
    private List<String> address;
    private String collectionName;
    private List<Column> column;
    private String dbName;
    public void setUserName(String userName) {
         this.userName = userName;
     }
     public String getUserName() {
         return userName;
     }

    public void setUserPassword(String userPassword) {
         this.userPassword = userPassword;
     }
     public String getUserPassword() {
         return userPassword;
     }

    public void setAddress(List<String> address) {
         this.address = address;
     }
     public List<String> getAddress() {
         return address;
     }

    public void setCollectionName(String collectionName) {
         this.collectionName = collectionName;
     }
     public String getCollectionName() {
         return collectionName;
     }

    public void setColumn(List<Column> column) {
         this.column = column;
     }
     public List<Column> getColumn() {
         return column;
     }

    public void setDbName(String dbName) {
         this.dbName = dbName;
     }
     public String getDbName() {
         return dbName;
     }

}