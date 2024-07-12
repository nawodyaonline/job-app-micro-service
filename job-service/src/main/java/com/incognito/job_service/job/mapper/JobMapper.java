package com.incognito.job_service.job.mapper;

import com.incognito.job_service.job.Job;
import com.incognito.job_service.job.dto.JobDTO;
import com.incognito.job_service.job.external.Company;
import com.incognito.job_service.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapJobToJobWithCompanyDTO(
            Job job,
            Company company,
            List<Review> reviews
            ) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setCompany(company);
        jobDTO.setReview(reviews);


        return jobDTO;
    }
}
