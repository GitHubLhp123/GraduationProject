package myproject.project.message.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "message_info", schema = "my_project", catalog = "")
public class MessageInfoEntity {
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createDate;
    private String createUserName;
    private String content;
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
    @Column(name = "create_date", nullable = false)
    @CreatedDate
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    public Timestamp getCreateDate() {
        return createDate;
    }

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
    @Column(name = "content", nullable = true, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "message_id", nullable = true, length = 128)
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageInfoEntity that = (MessageInfoEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(createUserName, that.createUserName) &&
                Objects.equals(content, that.content) &&
                Objects.equals(messageId, that.messageId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, createDate, createUserName, content, messageId);
    }
}
