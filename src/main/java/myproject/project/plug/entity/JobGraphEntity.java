package myproject.project.plug.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "job_graph", schema = "my_project", catalog = "")
public class JobGraphEntity {
    private String id;
    private String jobName;
    private String plungData;
    private String linkData;

    @Id
    @Column(name = "id", nullable = false, length = 128)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "job_name", nullable = true, length = 128)
    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    @Basic
    @Column(name = "plung_data", nullable = true, length = -1)
    public String getPlungData() {
        return plungData;
    }

    public void setPlungData(String plungData) {
        this.plungData = plungData;
    }

    @Basic
    @Column(name = "link_data", nullable = true, length = -1)
    public String getLinkData() {
        return linkData;
    }

    public void setLinkData(String linkData) {
        this.linkData = linkData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobGraphEntity that = (JobGraphEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(jobName, that.jobName) &&
                Objects.equals(plungData, that.plungData) &&
                Objects.equals(linkData, that.linkData);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, jobName, plungData, linkData);
    }
}
