package myproject.project.model.repository;


import myproject.project.model.entity.ModelEntity;
import myproject.project.model.entity.ModelFieldEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ModelFieldRepository extends JpaRepository<ModelFieldEntity, Integer>, JpaSpecificationExecutor<ModelFieldEntity> {

    List<ModelFieldEntity> findAllByModelId(String ModelId);

    @Transactional
    void deleteAllByModelId(String ModelId);
}
