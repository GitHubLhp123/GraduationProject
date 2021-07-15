package myproject.project.message.repository;

import myproject.project.message.entity.MessageInfoEntity;
import myproject.project.model.entity.ModelEntity;
import myproject.project.model.entity.ModelFieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MessageInfoRepository extends JpaRepository<MessageInfoEntity, String>, JpaSpecificationExecutor<MessageInfoEntity> {


     List<MessageInfoEntity> findAllByMessageIdOrderByCreateDateDesc(@Param("messageId") String messageId);


}
