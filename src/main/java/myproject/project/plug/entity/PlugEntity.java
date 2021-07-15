package myproject.project.plug.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "plug", schema = "my_project", catalog = "")
public class PlugEntity {
    private String id;
    private String plugName;
    private String plugLabel;

    @Id
    @Column(name = "id", nullable = false, length = 128)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    @Column(name = "plug_label", nullable = true, length = 128)
    public String getPlugLabel() {
        return plugLabel;
    }

    public void setPlugLabel(String plugLabel) {
        this.plugLabel = plugLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlugEntity that = (PlugEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(plugName, that.plugName) &&
                Objects.equals(plugLabel, that.plugLabel);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, plugName, plugLabel);
    }
}
