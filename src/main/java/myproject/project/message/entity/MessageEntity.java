package myproject.project.message.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "message", schema = "my_project", catalog = "")
public class MessageEntity {
    private String id;
    private String topic;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createDate;
    private String createUserName;
    private String currentUserName;
    private String messageName;
    private String title;
    private String state;
    private String messageId;

    @Id
    @Column(name = "id", nullable = false, length = 128)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "topic", nullable = false, length = 512)
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Basic
    @Column(name = "create_date", nullable = true)
    public Timestamp getCreateDate() {
        return createDate;
    }
    @CreationTimestamp
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "create_user_name", nullable = true, length = 32)
    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Basic
    @Column(name = "current_user_name", nullable = true, length = 32)
    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    @Basic
    @Column(name = "message_name", nullable = true, length = 128)
    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 256)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "state", nullable = true, length = 128)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntity that = (MessageEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(topic, that.topic) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(createUserName, that.createUserName) &&
                Objects.equals(currentUserName, that.currentUserName) &&
                Objects.equals(messageName, that.messageName) &&
                Objects.equals(title, that.title) &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, topic, createDate, createUserName, currentUserName, messageName, title, state);
    }

    @Basic
    @Column(name = "message_id", nullable = true, length = 128)
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
