package myproject.project.model.repository;


import myproject.project.model.entity.ModelEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ModelRepository extends JpaRepository<ModelEntity, String>, JpaSpecificationExecutor<ModelEntity> {

    @Query(value = "select * from model m where m.model_Name like %:modelName%   and  m.lastupd_User like %:userName%  and  m.state like %:state% and" + " m.create_Date>= :startDate and m.create_Date<= :endDate and m.team_name=:teamName", nativeQuery = true)
    List<ModelEntity> findAllByModelNameAndStateAndTAndTeamNameAndCreateDateBetween(@Param("modelName") String modelName, @Param("userName") String userName, @Param("state") String state, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("teamName") String teamName, Pageable pageable);

    @Query(value = "select count(1) from model m where m.model_Name like %:modelName%   and  m.lastupd_User like %:userName%  and  m.state like %:state% and" + " m.create_Date>= :startDate and m.create_Date<= :endDate and m.team_name=:teamName", nativeQuery = true)
    Integer countAllByModelNameAndStateAndTAndTeamNameAndCreateDateBetween(@Param("modelName") String modelName, @Param("userName") String userName, @Param("state") String state, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("teamName") String teamName);


    @Transactional
    void deleteByModelId(String ModelId);

    @Transactional
    Integer deleteAllByModelIdIn(List<String> ModelId);


    ModelEntity findByModelNameAndTeamNameAndDsName(String ModelName, String TeamName, String DsName);

    ModelEntity findByModelNameAndDsNameAndModelSchema(String ModelName, String DsName, String ModelSchema);

    ModelEntity findByModelId(String ModelId);

}
