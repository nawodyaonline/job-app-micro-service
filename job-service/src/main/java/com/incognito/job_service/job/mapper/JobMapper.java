package com.incognito.job_service.job.mapper;

import com.incognito.job_service.job.Job;
import com.incognito.job_service.job.dto.JobDTO;
import com.incognito.job_service.job.external.Company;

public class JobMapper {
    public static JobDTO mapJobToJobWithCompanyDTO(
            Job job,
            Company company) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setCompany(company);
        return jobDTO;
    }
}
