package myproject.project.message.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "message_operation", schema = "my_project", catalog = "")
public class MessageOperationEntity {
    private String id;
    private String messageId;
    private String userName;

    @Id
    @Column(name = "id", nullable = false, length = 128)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "message_id", nullable = true, length = 128)
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name = "user_name", nullable = true, length = 32)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageOperationEntity that = (MessageOperationEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(messageId, that.messageId) &&
                Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, messageId, userName);
    }

    public MessageOperationEntity(String id, String messageId, String userName) {
        this.id = id;
        this.messageId = messageId;
        this.userName = userName;
    }

    public MessageOperationEntity() {
    }

    @Override
    public String toString() {
        return "MessageOperationEntity{" +
                "id='" + id + '\'' +
                ", messageId='" + messageId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
