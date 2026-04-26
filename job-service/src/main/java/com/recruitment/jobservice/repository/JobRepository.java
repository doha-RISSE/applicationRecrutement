package com.recruitment.jobservice.repository;

import com.recruitment.jobservice.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}