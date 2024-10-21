package com.nrt.nrt.service;

import com.nrt.nrt.model.ProjectModel;
import com.nrt.nrt.model.RegisterModel;
import com.nrt.nrt.repository.ProjectRepository;
import com.nrt.nrt.repository.RegisterLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RegisterLogRepository registerLogRepository;

    // Fetch all projects
    public List<ProjectModel> getAllProjects() {
        return projectRepository.findAll();
    }

    // Handle bidding logic and deduct 1 bid
    public void bidOnProject(Long registerId, Long projectId) throws Exception {
        // Fetch user by registerId
        RegisterModel user = registerLogRepository.findById(registerId);
        if (user == null) {
            throw new Exception("User not found.");
        }

        // Check if the user has at least 1 bid
        if (user.getRemainingBids() <= 0) {
            throw new Exception("You don't have enough bids to place a bid on this project.");
        }

        // Proceed with the bid on the project
        // Optionally: You can add any other logic like saving the bid in the database

        // Deduct 1 bid
        int remainingBids = user.getRemainingBids() - 1;
        user.setRemainingBids(remainingBids);

        // Update user's bids in the database
        registerLogRepository.updateUserBids(user);
    }
}
