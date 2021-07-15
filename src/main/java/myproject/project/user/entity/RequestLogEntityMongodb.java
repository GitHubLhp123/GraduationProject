package myproject.project.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;


public class RequestLogEntityMongodb {

    private String id;
    private String userName;
    private String teamName;
    private String requestWay;
    private String url;
    private String parameterNames;
    private String responseStatus;
    private String classMethod;
    private String ip;
    private String args;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDt;

    public RequestLogEntityMongodb() {
    }

    public RequestLogEntityMongodb(String id, String userName, String teamName, String requestWay, String url, String parameterNames, String responseStatus, String classMethod, String ip, String args, Timestamp createDt) {
        this.id = id;
        this.userName = userName;
        this.teamName = teamName;
        this.requestWay = requestWay;
        this.url = url;
        this.parameterNames = parameterNames;
        this.responseStatus = responseStatus;
        this.classMethod = classMethod;
        this.ip = ip;
        this.args = args;
        this.createDt = createDt;
    }

    //@Id
    //@Column(name = "id", nullable = false, length = 128)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //@Basic
    //@Column(name = "user_name", nullable = true, length = 128)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //@Basic
    //@Column(name = "team_name", nullable = true, length = 128)
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    //@Basic
    //@Column(name = "request_way", nullable = true, length = 123)
    public String getRequestWay() {
        return requestWay;
    }

    public void setRequestWay(String requestWay) {
        this.requestWay = requestWay;
    }

    //@Basic
    //@Column(name = "url", nullable = true, length = 128)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //@Basic
    //@Column(name = "parameter_names", nullable = true, length = 123)
    public String getParameterNames() {
        return parameterNames;
    }

    public void setParameterNames(String parameterNames) {
        this.parameterNames = parameterNames;
    }

    //@Basic
    //@Column(name = "response_status", nullable = true, length = 128)
    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLogEntityMongodb that = (RequestLogEntityMongodb) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(teamName, that.teamName) &&
                Objects.equals(requestWay, that.requestWay) &&
                Objects.equals(url, that.url) &&
                Objects.equals(parameterNames, that.parameterNames) &&
                Objects.equals(responseStatus, that.responseStatus);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userName, teamName, requestWay, url, parameterNames, responseStatus);
    }

    //@Basic
    //@Column(name = "CLASS_METHOD", nullable = true, length = 123)
    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }

    //@Basic
    //@Column(name = "ip", nullable = true, length = 50)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    //@Basic
    //@Column(name = "ARGS", nullable = true, length = 1000)
    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    //@Basic
    //@Column(name = "create_dt", nullable = true)
    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }
}
