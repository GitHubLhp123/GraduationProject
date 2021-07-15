package myproject.project.team.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "team_level", schema = "my_project", catalog = "")
public class TeamLevelEntity {
    private String id;
    private String label;
    private String value;
    private String teamName;

    @Id
    @Column(name = "id", nullable = false, length = 128)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "label", nullable = true, length = 128)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Basic
    @Column(name = "value", nullable = true, length = 128)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "team_name", nullable = true, length = 123)
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamLevelEntity that = (TeamLevelEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(label, that.label) &&
                Objects.equals(value, that.value) &&
                Objects.equals(teamName, that.teamName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, label, value, teamName);
    }
}
