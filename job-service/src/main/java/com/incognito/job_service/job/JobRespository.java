package com.incognito.job_service.job;

import org.springframework.data.jpa.repository.JpaRepository;


public interface JobRespository extends JpaRepository<Job, Long> {
}
