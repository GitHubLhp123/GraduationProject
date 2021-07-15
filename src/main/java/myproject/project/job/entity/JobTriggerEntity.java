package myproject.project.job.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "job_trigger", schema = "my_project", catalog = "")
public class JobTriggerEntity {
    private String jobName;
    private String jobLabel;
    private String batchNo;
    private String batchType;
    private String isTurnover;
    private String brokerId;
    private String brokerIp;
    private Timestamp createDt;
    private Timestamp startDt;
    private Timestamp completeDt;
    private Timestamp lastupd;
    private String message;
    private String state;
    private String teamName;
    private String isNew;
    private Integer priority;
    private Integer duration;
    private String workgroup;
    private String memberName;
    private Integer runState;
    private Integer isValid;
    private String rtnCode;
    private Integer actualRedoNum;
    private Long avgDuration;
    private String jobTrigId;
    private String parentCode;
    private String initiatedTeam;

    @Basic
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
    @Column(name = "batch_no", nullable = true, length = 32)
    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    @Basic
    @Column(name = "batch_type", nullable = true, length = 16)
    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    @Basic
    @Column(name = "is_turnover", nullable = true, length = 100)
    public String getIsTurnover() {
        return isTurnover;
    }

    public void setIsTurnover(String isTurnover) {
        this.isTurnover = isTurnover;
    }

    @Basic
    @Column(name = "broker_id", nullable = true, length = 16)
    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    @Basic
    @Column(name = "broker_ip", nullable = true, length = 16)
    public String getBrokerIp() {
        return brokerIp;
    }

    public void setBrokerIp(String brokerIp) {
        this.brokerIp = brokerIp;
    }

    @Basic
    @Column(name = "create_dt", nullable = true)
    public Timestamp getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Timestamp createDt) {
        this.createDt = createDt;
    }

    @Basic
    @Column(name = "start_dt", nullable = true)
    public Timestamp getStartDt() {
        return startDt;
    }

    public void setStartDt(Timestamp startDt) {
        this.startDt = startDt;
    }

    @Basic
    @Column(name = "complete_dt", nullable = true)
    public Timestamp getCompleteDt() {
        return completeDt;
    }

    public void setCompleteDt(Timestamp completeDt) {
        this.completeDt = completeDt;
    }

    @Basic
    @Column(name = "lastupd", nullable = true)
    public Timestamp getLastupd() {
        return lastupd;
    }

    public void setLastupd(Timestamp lastupd) {
        this.lastupd = lastupd;
    }

    @Basic
    @Column(name = "message", nullable = true, length = -1)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
    @Column(name = "team_name", nullable = true, length = 32)
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Basic
    @Column(name = "is_new", nullable = true, length = 32)
    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    @Basic
    @Column(name = "priority", nullable = true)
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "duration", nullable = true)
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "workgroup", nullable = true, length = 64)
    public String getWorkgroup() {
        return workgroup;
    }

    public void setWorkgroup(String workgroup) {
        this.workgroup = workgroup;
    }

    @Basic
    @Column(name = "member_name", nullable = true, length = 128)
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Basic
    @Column(name = "run_state", nullable = true)
    public Integer getRunState() {
        return runState;
    }

    public void setRunState(Integer runState) {
        this.runState = runState;
    }

    @Basic
    @Column(name = "is_valid", nullable = true)
    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    @Basic
    @Column(name = "rtn_code", nullable = true, length = 5)
    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    @Basic
    @Column(name = "actual_redo_num", nullable = true)
    public Integer getActualRedoNum() {
        return actualRedoNum;
    }

    public void setActualRedoNum(Integer actualRedoNum) {
        this.actualRedoNum = actualRedoNum;
    }

    @Basic
    @Column(name = "avg_duration", nullable = true)
    public Long getAvgDuration() {
        return avgDuration;
    }

    public void setAvgDuration(Long avgDuration) {
        this.avgDuration = avgDuration;
    }

    @Id
    @Column(name = "job_trig_id", nullable = false, length = 255)
    public String getJobTrigId() {
        return jobTrigId;
    }

    public void setJobTrigId(String jobTrigId) {
        this.jobTrigId = jobTrigId;
    }

    @Basic
    @Column(name = "parent_code", nullable = true, length = 128)
    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    @Basic
    @Column(name = "initiated_team", nullable = true, length = 32)
    public String getInitiatedTeam() {
        return initiatedTeam;
    }

    public void setInitiatedTeam(String initiatedTeam) {
        this.initiatedTeam = initiatedTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobTriggerEntity that = (JobTriggerEntity) o;
        return Objects.equals(jobName, that.jobName) &&
                Objects.equals(jobLabel, that.jobLabel) &&
                Objects.equals(batchNo, that.batchNo) &&
                Objects.equals(batchType, that.batchType) &&
                Objects.equals(isTurnover, that.isTurnover) &&
                Objects.equals(brokerId, that.brokerId) &&
                Objects.equals(brokerIp, that.brokerIp) &&
                Objects.equals(createDt, that.createDt) &&
                Objects.equals(startDt, that.startDt) &&
                Objects.equals(completeDt, that.completeDt) &&
                Objects.equals(lastupd, that.lastupd) &&
                Objects.equals(message, that.message) &&
                Objects.equals(state, that.state) &&
                Objects.equals(teamName, that.teamName) &&
                Objects.equals(isNew, that.isNew) &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(duration, that.duration) &&
                Objects.equals(workgroup, that.workgroup) &&
                Objects.equals(memberName, that.memberName) &&
                Objects.equals(runState, that.runState) &&
                Objects.equals(isValid, that.isValid) &&
                Objects.equals(rtnCode, that.rtnCode) &&
                Objects.equals(actualRedoNum, that.actualRedoNum) &&
                Objects.equals(avgDuration, that.avgDuration) &&
                Objects.equals(jobTrigId, that.jobTrigId) &&
                Objects.equals(parentCode, that.parentCode) &&
                Objects.equals(initiatedTeam, that.initiatedTeam);
    }

    @Override
    public int hashCode() {

        return Objects.hash(jobName, jobLabel, batchNo, batchType, isTurnover, brokerId, brokerIp, createDt, startDt, completeDt, lastupd, message, state, teamName, isNew, priority, duration, workgroup, memberName, runState, isValid, rtnCode, actualRedoNum, avgDuration, jobTrigId, parentCode, initiatedTeam);
    }
}
