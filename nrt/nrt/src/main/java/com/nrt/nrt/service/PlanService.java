package com.nrt.nrt.service;

import com.nrt.nrt.model.PlanModel;
import com.nrt.nrt.repository.PlanRepository; // Assume you have a PlanRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {
    @Autowired
    private PlanRepository planRepository;

    public List<PlanModel> getAllPlans() {
        return planRepository.findAll(); // Method to fetch all plans from DB
    }

    public PlanModel findById(Long planId) {
        return planRepository.findById(planId); // Method to fetch a plan by ID
    }

	
}
