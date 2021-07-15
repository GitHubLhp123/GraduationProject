package myproject.project.sysInfo.repository;


import myproject.project.sysInfo.model.SysInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SysInfoRepository  extends JpaRepository<SysInfoEntity, String>, JpaSpecificationExecutor<SysInfoEntity> {

    @Query(value = " select  * from (select  * from sys_info order by create_dt desc ) tab group by tab.host_name", nativeQuery = true)
    List<SysInfoEntity> findBySQl();
}
