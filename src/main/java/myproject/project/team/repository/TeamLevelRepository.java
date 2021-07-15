package myproject.project.team.repository;


import myproject.project.model.entity.ModelEntity;
import myproject.project.team.entity.TeamLevelEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeamLevelRepository extends JpaRepository<TeamLevelEntity, String>, JpaSpecificationExecutor<TeamLevelEntity> {


    List<TeamLevelEntity> findByTeamName(String teamName);

    @Transactional
    void deleteAllByTeamName(String teamName);

}
