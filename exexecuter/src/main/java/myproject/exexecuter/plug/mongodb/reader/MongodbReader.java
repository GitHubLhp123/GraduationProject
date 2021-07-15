package myproject.exexecuter.plug.mongodb.reader;


import java.util.List;

/**
 * Auto-generated: 2021-02-19 10:10:46
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class MongodbReader {

    private String key;
    private String label;
    private String name;
    private String dsName;
    private String dsSchema;
    private List<Column> column;
    private String collectionName;

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

    public String getDsSchema() {
        return dsSchema;
    }

    public void setDsSchema(String dsSchema) {
        this.dsSchema = dsSchema;
    }

    public List<Column> getColumn() {
        return column;
    }

    public void setColumn(List<Column> column) {
        this.column = column;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
}