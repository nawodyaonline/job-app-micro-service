package com.incognito.job_service.job.impl;


import com.incognito.job_service.job.Job;
import com.incognito.job_service.job.JobRespository;
import com.incognito.job_service.job.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    JobRespository jobRespository;

    public JobServiceImpl(JobRespository jobRespository) {
        this.jobRespository = jobRespository;
    }

    private Long nextId = 1L;

    @Override
    public List<Job> findAll() {
        return jobRespository.findAll();
    }

    @Override
    public void createJob(Job job) {
        job.setId(nextId++);
        jobRespository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRespository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try{
            jobRespository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRespository.findById(id);
        if(jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setDescription(updatedJob.getDescription());
            job.setTitle(updatedJob.getTitle());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRespository.save(job);
            return true;
        }
        return false;
    }
}
