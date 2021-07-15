package myproject.exexecuter.plug.mysql.writer;

public class MysqlWriter {

    private String key;
    private String label;
    private String name;
    private String dsName;
    private String tableName;
    private String dsSchema;
    private String preSql;
    private String columns;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDsSchema() {
        return dsSchema;
    }

    public void setDsSchema(String dsSchema) {
        this.dsSchema = dsSchema;
    }

    public String getPreSql() {
        return preSql;
    }

    public void setPreSql(String preSql) {
        this.preSql = preSql;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }


    @Override
    public String toString() {


        return "";
    }


}
