package myproject.project.ds.repository;


import myproject.project.ds.entity.DataQueryLogEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface DataQueryLogRepository extends JpaRepository<DataQueryLogEntity, String>, JpaSpecificationExecutor<DataQueryLogEntity> {



    List<DataQueryLogEntity> findAllByUserNameLikeAndRunSqlLikeAndRunTimeLikeAndDsNameLike(String userName, String runSql, String runTime, String dsName, Pageable pageable);

    Integer countAllByUserNameLikeAndRunSqlLikeAndRunTimeLikeAndDsNameLike(String userName, String runSql, String runTime, String dsName);

}
