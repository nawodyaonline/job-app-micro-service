package com.incognito.job_service.job.impl;


import com.incognito.job_service.job.Job;
import com.incognito.job_service.job.JobRespository;
import com.incognito.job_service.job.JobService;
import com.incognito.job_service.job.dto.JobWithCompanyDTO;
import com.incognito.job_service.job.external.Company;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    JobRespository jobRespository;
    RestTemplate restTemplate;


    @Autowired
    public JobServiceImpl(JobRespository jobRespository, RestTemplate restTemplate) {
        this.jobRespository = jobRespository;
        this.restTemplate = restTemplate;
    }

    private Long nextId = 1L;

    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRespository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOS = new ArrayList<>();

        return  jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private JobWithCompanyDTO convertToDto(Job job) {
//        RestTemplate restTemplate = new RestTemplate();
        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setJob(job);
        Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId(), Company.class);
        jobWithCompanyDTO.setCompany(company);
        return jobWithCompanyDTO;

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
        try {
            jobRespository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRespository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setDescription(updatedJob.getDescription());
            job.setTitle(updatedJob.getTitle());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            job.setCompanyId(updatedJob.getCompanyId());
            jobRespository.save(job);
            return true;
        }
        return false;
    }
}
