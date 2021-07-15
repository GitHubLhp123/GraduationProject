package myproject.project.ds.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "data_query_log", schema = "my_project", catalog = "")
public class DataQueryLogEntity {
    private String teamName;
    private String userId;
    private String dsName;
    private String dsSchema;
    private String runSql;
    private String ipAddress;
    private String resultType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp runTime;
    private String logText;
    private String sqlIds;
    private String logId;
    private String dsType;
    private String copyContent;
    private String userName;

    @Basic
    @Column(name = "team_name", nullable = true, length = 32)
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Basic
    @Column(name = "user_id", nullable = true, length = 32)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "ds_name", nullable = true, length = 32)
    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    @Basic
    @Column(name = "ds_schema", nullable = true, length = 32)
    public String getDsSchema() {
        return dsSchema;
    }

    public void setDsSchema(String dsSchema) {
        this.dsSchema = dsSchema;
    }

    @Basic
    @Column(name = "run_sql", nullable = true, length = -1)
    public String getRunSql() {
        return runSql;
    }

    public void setRunSql(String runSql) {
        this.runSql = runSql;
    }

    @Basic
    @Column(name = "ip_address", nullable = true, length = 32)
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Basic
    @Column(name = "result_type", nullable = true, length = 16)
    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    @Basic
    @Column(name = "run_time", nullable = true)
    public Timestamp getRunTime() {
        return runTime;
    }

    public void setRunTime(Timestamp runTime) {
        this.runTime = runTime;
    }

    @Basic
    @Column(name = "log_text", nullable = true, length = -1)
    public String getLogText() {
        return logText;
    }

    public void setLogText(String logText) {
        this.logText = logText;
    }

    @Basic
    @Column(name = "sql_ids", nullable = true, length = 128)
    public String getSqlIds() {
        return sqlIds;
    }

    public void setSqlIds(String sqlIds) {
        this.sqlIds = sqlIds;
    }

    @Id
    @Column(name = "log_id", nullable = false, length = 32)
    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    @Basic
    @Column(name = "ds_type", nullable = true, length = 16)
    public String getDsType() {
        return dsType;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    @Basic
    @Column(name = "copy_content", nullable = true, length = 128)
    public String getCopyContent() {
        return copyContent;
    }

    public void setCopyContent(String copyContent) {
        this.copyContent = copyContent;
    }

    @Basic
    @Column(name = "user_name", nullable = true, length = 128)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataQueryLogEntity that = (DataQueryLogEntity) o;
        return Objects.equals(teamName, that.teamName) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(dsName, that.dsName) &&
                Objects.equals(dsSchema, that.dsSchema) &&
                Objects.equals(runSql, that.runSql) &&
                Objects.equals(ipAddress, that.ipAddress) &&
                Objects.equals(resultType, that.resultType) &&
                Objects.equals(runTime, that.runTime) &&
                Objects.equals(logText, that.logText) &&
                Objects.equals(sqlIds, that.sqlIds) &&
                Objects.equals(logId, that.logId) &&
                Objects.equals(dsType, that.dsType) &&
                Objects.equals(copyContent, that.copyContent) &&
                Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(teamName, userId, dsName, dsSchema, runSql, ipAddress, resultType, runTime, logText, sqlIds, logId, dsType, copyContent, userName);
    }
}
