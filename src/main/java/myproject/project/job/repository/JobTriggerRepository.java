package myproject.project.job.repository;

import myproject.project.job.entity.JobEntity;
import myproject.project.job.entity.JobTriggerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface JobTriggerRepository extends JpaRepository<JobTriggerEntity, String>, JpaSpecificationExecutor<JobTriggerEntity> {


    List<JobTriggerEntity> findAllByJobName(String JobName, Pageable pageable);

    Integer countAllByJobName(String JobName);


    List<JobTriggerEntity> findAllByJobNameLikeAndJobLabelLikeAndStateLikeAndTeamNameAndIsValidOrderByJobName(String jobName, String jobLabel, String state, String teamName, Integer IsValid, Pageable pageable);


    Integer countAllByJobNameLikeAndJobLabelLikeAndStateLikeAndTeamNameAndIsValid(String jobName, String jobLabel, String state, String teamName, Integer IsValid);

}