package myproject.project.ds.repository;

import myproject.project.ds.entity.TeamDsEntity;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TeamDsRepository extends JpaRepository<TeamDsEntity, String>, JpaSpecificationExecutor<TeamDsEntity> {

    List<TeamDsEntity> findAllByTeamName(String teamName);

    TeamDsEntity findByTeamNameAndDsName(@Param("TeamName") String TeamName, @Param("DsName") String DsName);

    TeamDsEntity findByDsId(@Param("DsId") String DsId);

    List<TeamDsEntity> findByTeamNameAndDsType(@Param("TeamName") String TeamName, @Param("DsType") String DsType);
}