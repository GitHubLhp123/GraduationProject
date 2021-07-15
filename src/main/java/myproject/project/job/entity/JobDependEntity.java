package myproject.project.job.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "job_depend", schema = "my_project", catalog = "")
public class JobDependEntity {
    private String jobName;
    private String dependName;
    private String dependType;
    private String batchCalExp;
    private String output;
    private String procName;
    private String isDefault;
    private String dependId;

    @Basic
    @Column(name = "job_name", nullable = true, length = 128)
    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Basic
    @Column(name = "depend_name", nullable = true, length = 128)
    public String getDependName() {
        return dependName;
    }

    public void setDependName(String dependName) {
        this.dependName = dependName;
    }

    @Basic
    @Column(name = "depend_type", nullable = true, length = 8)
    public String getDependType() {
        return dependType;
    }

    public void setDependType(String dependType) {
        this.dependType = dependType;
    }

    @Basic
    @Column(name = "batch_cal_exp", nullable = true, length = 256)
    public String getBatchCalExp() {
        return batchCalExp;
    }

    public void setBatchCalExp(String batchCalExp) {
        this.batchCalExp = batchCalExp;
    }

    @Basic
    @Column(name = "output", nullable = true, length = -1)
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Basic
    @Column(name = "proc_name", nullable = true, length = 128)
    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    @Basic
    @Column(name = "is_default", nullable = true, length = 8)
    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    @Id
    @Column(name = "depend_id", nullable = false, length = 128)
    public String getDependId() {
        return dependId;
    }

    public void setDependId(String dependId) {
        this.dependId = dependId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobDependEntity that = (JobDependEntity) o;
        return Objects.equals(jobName, that.jobName) &&
                Objects.equals(dependName, that.dependName) &&
                Objects.equals(dependType, that.dependType) &&
                Objects.equals(batchCalExp, that.batchCalExp) &&
                Objects.equals(output, that.output) &&
                Objects.equals(procName, that.procName) &&
                Objects.equals(isDefault, that.isDefault) &&
                Objects.equals(dependId, that.dependId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(jobName, dependName, dependType, batchCalExp, output, procName, isDefault, dependId);
    }
}
