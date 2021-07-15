package myproject.project.plug.repository;

import myproject.project.plug.entity.JobGraphEntity;
import myproject.project.plug.entity.PlugEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface PlugRepository extends JpaRepository<PlugEntity, String>, JpaSpecificationExecutor<PlugEntity> {


}
