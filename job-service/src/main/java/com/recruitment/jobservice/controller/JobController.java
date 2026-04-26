package com.recruitment.jobservice.controller;

import com.recruitment.jobservice.model.Job;
import com.recruitment.jobservice.service.JobService;
import com.recruitment.jobservice.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;
    private final JwtUtil jwtUtil;

    // 👇 INJECTION OBLIGATOIRE
    public JobController(JobService jobService, JwtUtil jwtUtil) {
        this.jobService = jobService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public Job create(@RequestBody Job job,
                      @RequestHeader("Authorization") String token) {

        Long recruiterId = jwtUtil.extractUserId(token);

        job.setRecruiterId(recruiterId);

        return jobService.save(job);
    }


    @GetMapping
    public java.util.List<Job> getAll() {
        return jobService.getAll();
    }
}