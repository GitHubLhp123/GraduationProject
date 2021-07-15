package myproject.project.plug.repository;

import myproject.project.plug.entity.JobGraphEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;


public interface JobGraphRepository extends JpaRepository<JobGraphEntity, String>, JpaSpecificationExecutor<JobGraphEntity> {


    JobGraphEntity findByJobName(String jobName);

    @Transactional
    void deleteByJobName(String jobName);

}
