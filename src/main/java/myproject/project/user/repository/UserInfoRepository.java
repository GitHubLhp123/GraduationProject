package myproject.project.user.repository;


import myproject.project.user.entity.UserInfoEntity;
import myproject.project.user.entity.UserPagePermissionsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface UserInfoRepository extends JpaRepository<UserInfoEntity, String>, JpaSpecificationExecutor<UserInfoEntity> {


    UserInfoEntity findByUserName(String userName);


    List<UserInfoEntity> findAllByTeamName(String TeamName, Pageable pageable);

    Integer countAllByTeamName(String TeamName);
}
