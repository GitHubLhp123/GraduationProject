package myproject.project.message.repository;

import myproject.project.message.entity.MessageInfoEntity;
import myproject.project.message.entity.MessageOperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MessageOperationRepository extends JpaRepository<MessageOperationEntity, String>, JpaSpecificationExecutor<MessageOperationEntity> {


}
