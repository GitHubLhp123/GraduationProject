package myproject.exexecuter.plug.mysql.writer;

public class DsParameter {

    private String jdbcUrl;
    private String password;
    private String username;


    public DsParameter() {
    }
    public DsParameter(String dsName,String dsSchema,String teamName) {


    }


    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
