package com.incognito.job_service.job.impl;


import com.incognito.job_service.job.Job;
import com.incognito.job_service.job.JobRespository;
import com.incognito.job_service.job.JobService;
import com.incognito.job_service.job.clients.CompanyClient;
import com.incognito.job_service.job.clients.ReviewClient;
import com.incognito.job_service.job.dto.JobDTO;
import com.incognito.job_service.job.external.Company;
import com.incognito.job_service.job.external.Review;
import com.incognito.job_service.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;

    int attempts = 0;

    JobRespository jobRespository;
    RestTemplate restTemplate;


    @Autowired
    public JobServiceImpl(JobRespository jobRespository, RestTemplate restTemplate, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRespository = jobRespository;
        this.restTemplate = restTemplate;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    private Long nextId = 1L;

    @Override
    @RateLimiter(name = "companyBreaker",
            fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        System.out.println("Attempt: " + ++attempts);
        List<Job> jobs = jobRespository.findAll();

        return  jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<String> companyBreakerFallback(Exception e){
        List<String> list = new ArrayList<>();
        list.add("Hello");
        return list;
    }

    private JobDTO convertToDto(Job job) {
        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
        return JobMapper.mapJobToJobWithCompanyDTO(job, company, reviews);
    }

    @Override
    public void createJob(Job job) {
        job.setId(nextId++);
        jobRespository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRespository.findById(id).orElse(null);
        return convertToDto(job);
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
