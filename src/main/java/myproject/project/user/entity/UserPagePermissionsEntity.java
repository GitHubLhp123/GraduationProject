package myproject.project.user.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "user_page_permissions", schema = "my_project", catalog = "")
public class UserPagePermissionsEntity {
    private String id;
    private String userName;
    private String teamName;
    private String permissions;
    private String role;
    private String teamLabel;
    private Timestamp createDate;
    private String state;

    public UserPagePermissionsEntity() {
    }

    public UserPagePermissionsEntity(String id, String userName, String teamName, String permissions, String role, String teamLabel) {
        this.id = id;
        this.userName = userName;
        this.teamName = teamName;
        this.permissions = permissions;
        this.role = role;
        this.teamLabel = teamLabel;
    }

    @Id
    @Column(name = "id", nullable = false, length = 128)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name", nullable = true, length = 20)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "team_name", nullable = true, length = 20)
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Basic
    @Column(name = "permissions", nullable = true, length = 128)
    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    @Basic
    @Column(name = "role", nullable = true, length = 20)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Basic
    @Column(name = "team_label", nullable = true, length = 20)
    public String getTeamLabel() {
        return teamLabel;
    }

    public void setTeamLabel(String teamLabel) {
        this.teamLabel = teamLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPagePermissionsEntity that = (UserPagePermissionsEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(teamName, that.teamName) &&
                Objects.equals(permissions, that.permissions) &&
                Objects.equals(role, that.role) &&
                Objects.equals(teamLabel, that.teamLabel);
    }

    @Override
    public String toString() {
        return "UserPagePermissionsEntity{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", teamName='" + teamName + '\'' +
                ", permissions='" + permissions + '\'' +
                ", role='" + role + '\'' +
                ", teamLabel='" + teamLabel + '\'' +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userName, teamName, permissions, role, teamLabel);
    }

    @Basic
    @Column(name = "create_date", nullable = true)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "state", nullable = true, length = 5)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
