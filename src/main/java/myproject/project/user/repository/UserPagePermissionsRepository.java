package myproject.project.user.repository;


import myproject.project.user.entity.UserPagePermissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface UserPagePermissionsRepository extends JpaRepository<UserPagePermissionsEntity, Integer>, JpaSpecificationExecutor<UserPagePermissionsEntity> {

    UserPagePermissionsEntity findByTeamNameAndUserName(String teamName, String UserName);


    List<UserPagePermissionsEntity> findAllByUserName(String UserName);
    Integer countAllByTeamName(String teamName);
}
