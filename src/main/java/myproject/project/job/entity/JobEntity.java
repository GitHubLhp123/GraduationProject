package myproject.project.job.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "job", schema = "my_project", catalog = "")
public class JobEntity {
    private String jobName;
    private String jobLabel;
    private String state;
    private String cron;

    private Date startDate;

    private Date endDate;
    private String emails;
    private Integer retryCount;
    private String createUserName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp lastUpdTime;
    private String lastUserName;
    private String teamName;
    private String outputTabs;
    private String inputTabs;

    @Id
    @Column(name = "job_name", nullable = false, length = 128)
    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Basic
    @Column(name = "job_label", nullable = true, length = 128)
    public String getJobLabel() {
        return jobLabel;
    }

    public void setJobLabel(String jobLabel) {
        this.jobLabel = jobLabel;
    }

    @Basic
    @Column(name = "state", nullable = true, length = 20)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "cron", nullable = true, length = 50)
    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    @Basic
    @Column(name = "start_date", nullable = true)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = true)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "emails", nullable = true, length = 500)
    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    @Basic
    @Column(name = "retry_count", nullable = true)
    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    @Basic
    @Column(name = "create_user_name", nullable = true, length = 20)
    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "last_upd_time", nullable = true)
    public Timestamp getLastUpdTime() {
        return lastUpdTime;
    }

    public void setLastUpdTime(Timestamp lastUpdTime) {
        this.lastUpdTime = lastUpdTime;
    }

    @Basic
    @Column(name = "last_user_name", nullable = true, length = 20)
    public String getLastUserName() {
        return lastUserName;
    }

    public void setLastUserName(String lastUserName) {
        this.lastUserName = lastUserName;
    }

    @Basic
    @Column(name = "team_name", nullable = true, length = 100)
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Basic
    @Column(name = "output_tabs", nullable = true, length = 256)
    public String getOutputTabs() {
        return outputTabs;
    }

    public void setOutputTabs(String outputTabs) {
        this.outputTabs = outputTabs;
    }

    @Basic
    @Column(name = "input_tabs", nullable = true, length = 256)
    public String getInputTabs() {
        return inputTabs;
    }

    public void setInputTabs(String inputTabs) {
        this.inputTabs = inputTabs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobEntity jobEntity = (JobEntity) o;
        return Objects.equals(jobName, jobEntity.jobName) &&
                Objects.equals(jobLabel, jobEntity.jobLabel) &&
                Objects.equals(state, jobEntity.state) &&
                Objects.equals(cron, jobEntity.cron) &&
                Objects.equals(startDate, jobEntity.startDate) &&
                Objects.equals(endDate, jobEntity.endDate) &&
                Objects.equals(emails, jobEntity.emails) &&
                Objects.equals(retryCount, jobEntity.retryCount) &&
                Objects.equals(createUserName, jobEntity.createUserName) &&
                Objects.equals(createTime, jobEntity.createTime) &&
                Objects.equals(lastUpdTime, jobEntity.lastUpdTime) &&
                Objects.equals(lastUserName, jobEntity.lastUserName) &&
                Objects.equals(teamName, jobEntity.teamName) &&
                Objects.equals(outputTabs, jobEntity.outputTabs) &&
                Objects.equals(inputTabs, jobEntity.inputTabs);
    }

    @Override
    public int hashCode() {

        return Objects.hash(jobName, jobLabel, state, cron, startDate, endDate, emails, retryCount, createUserName, createTime, lastUpdTime, lastUserName, teamName, outputTabs, inputTabs);
    }
}
