package myproject.project.team.repository;


import myproject.project.team.entity.TeamLevelEntity;
import myproject.project.team.entity.TeamPlugEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeamPlugRepository extends JpaRepository<TeamPlugEntity, String>, JpaSpecificationExecutor<TeamPlugEntity> {


    List<TeamPlugEntity> findByTeamName(String teamName);
    @Transactional
    void  deleteById(String id);


}
