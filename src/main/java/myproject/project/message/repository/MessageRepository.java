package myproject.project.message.repository;

import myproject.project.message.entity.MessageEntity;
import myproject.project.message.entity.MessageInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;


public interface MessageRepository extends JpaRepository<MessageEntity, String>, JpaSpecificationExecutor<MessageEntity> {


    public MessageEntity findByMessageId(String MessageId);



}
