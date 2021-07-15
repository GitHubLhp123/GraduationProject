package myproject.project.job.repository;

import myproject.project.ds.entity.DataQueryLogEntity;
import myproject.project.job.entity.JobEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface JobRepository extends JpaRepository<JobEntity, String>, JpaSpecificationExecutor<JobEntity> {


    List<JobEntity> findAllByJobNameLikeAndJobLabelLikeAndStateLikeAndLastUserNameLikeAndTeamName(String JobName, String JobLabel, String State, String LastUserName, String teamName, Pageable pageable);

    Integer countAllByJobNameLikeAndJobLabelLikeAndStateLikeAndLastUserNameLikeAndTeamName(String JobName, String JobLabel, String State, String LastUserName, String teamName);

    JobEntity findByJobName(String jobName);

    List<JobEntity> findAllByInputTabsLike(String InputTabs);

}