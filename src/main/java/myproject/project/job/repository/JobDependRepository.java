package myproject.project.job.repository;

import myproject.project.job.entity.JobDependEntity;
import myproject.project.job.entity.JobEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface JobDependRepository extends JpaRepository<JobDependEntity, String>, JpaSpecificationExecutor<JobDependEntity> {

    List<JobDependEntity> findAllByJobName(String jobName);

    @Transactional
    void deleteAllByJobName(String jobName);
}