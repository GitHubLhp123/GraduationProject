package myproject.project.user.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_info", schema = "my_project", catalog = "")
public class UserInfoEntity {
    private String userId;
    private String userName;
    private String email;
    private String phone;
    private String id;
    private String teamName;
    private String teamLabel;

    @Basic
    @Column(name = "user_id", nullable = true, length = 128)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_name", nullable = true, length = 128)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 128)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 128)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
    @Column(name = "team_name", nullable = true, length = 128)
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Basic
    @Column(name = "team_label", nullable = true, length = 18)
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
        UserInfoEntity that = (UserInfoEntity) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(id, that.id) &&
                Objects.equals(teamName, that.teamName) &&
                Objects.equals(teamLabel, that.teamLabel);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, userName, email, phone, id, teamName, teamLabel);
    }
}
