package myproject.project.team.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "team_plug", schema = "my_project", catalog = "")
public class TeamPlugEntity {
    private String id;
    private String teamName;
    private String plugName;
    private String plugLabel;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createDt;

    @Id
    @Column(name = "id", nullable = false, length = 128)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "team_name", nullable = true, length = 128)
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Basic
    @Column(name = "plug_name", nullable = true, length = 128)
    public String getPlugName() {
        return plugName;
    }

    public void setPlugName(String plugName) {
        this.plugName = plugName;
    }

    @Basic
    @Column(name = "plug_Label", nullable = true, length = 128)
    public String getPlugLabel() {
        return plugLabel;
    }

    public void setPlugLabel(String plugLabel) {
        this.plugLabel = plugLabel;
    }

    @Basic
    @Column(name = "create_dt", nullable = true)
    public Timestamp getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Timestamp createDt) {
        this.createDt = createDt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamPlugEntity that = (TeamPlugEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(teamName, that.teamName) &&
                Objects.equals(plugName, that.plugName) &&
                Objects.equals(plugLabel, that.plugLabel) &&
                Objects.equals(createDt, that.createDt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, teamName, plugName, plugLabel, createDt);
    }
}
