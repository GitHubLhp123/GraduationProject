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
public class Connection {

    private String jdbcUrl;
    private List<String> table;

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setTable(List<String> table) {
        this.table = table;
    }

    public List<String> getTable() {
        return table;
    }

}