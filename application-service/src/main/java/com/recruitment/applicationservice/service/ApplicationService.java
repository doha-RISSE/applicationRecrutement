package com.recruitment.applicationservice.service;

import com.recruitment.applicationservice.model.Application;
import com.recruitment.applicationservice.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepository repo;

    public ApplicationService(ApplicationRepository repo) {
        this.repo = repo;
    }

    public Application apply(Application app) {
        return repo.save(app);
    }

    public Optional<Application> getById(Long id) {
        return repo.findById(id);
    }
}