package com.recruitment.applicationservice.service;

import com.recruitment.applicationservice.model.Application;
import com.recruitment.applicationservice.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.web.client.RestTemplate;
import com.recruitment.applicationservice.dto.Job;

@Service
public class ApplicationService {

    private final ApplicationRepository repo;
    private final RestTemplate restTemplate;

    public ApplicationService(ApplicationRepository repo, RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;}

    public Application apply(Application app) {
        return repo.save(app);
    }

    public Optional<Application> getById(Long id) {
        return repo.findById(id);
    }
    public Application updateStatus(Application app, String status) {
        app.setStatus(status);
        return repo.save(app);
    }
    public boolean isRecruiterOwnerOfJob(Long jobId, Long userId) {

        // appel HTTP vers job-service
        Job job = restTemplate.getForObject(
                "http://localhost:8082/api/jobs/" + jobId,
                Job.class
        );

        return job.getRecruiterId().equals(userId);
    }
}