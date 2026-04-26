package com.recruitment.applicationservice.repository;

import com.recruitment.applicationservice.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}