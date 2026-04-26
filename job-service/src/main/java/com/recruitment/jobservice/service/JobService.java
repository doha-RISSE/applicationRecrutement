package com.recruitment.jobservice.service;

import com.recruitment.jobservice.model.Job;
import com.recruitment.jobservice.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;


    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job save(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> getAll() {
        return jobRepository.findAll();
    }
    // 🔍 SEARCH
    public List<Job> search(String keyword) {
        return jobRepository.findByTitleContainingOrDescriptionContaining(keyword, keyword);
    }
}