package com.incognito.job_service.job.mapper;

import com.incognito.job_service.job.Job;
import com.incognito.job_service.job.dto.JobWithCompanyDTO;
import com.incognito.job_service.job.external.Company;

public class JobMapper {
    public static JobWithCompanyDTO mapJobToJobWithCompanyDTO(
            Job job,
            Company company) {
        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setId(job.getId());
        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setMinSalary(job.getMinSalary());
        jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDTO.setLocation(job.getLocation());
        jobWithCompanyDTO.setCompany(company);
        return jobWithCompanyDTO;
    }
}
