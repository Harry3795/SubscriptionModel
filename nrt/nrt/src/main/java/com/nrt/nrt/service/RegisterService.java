package com.nrt.nrt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nrt.nrt.model.RegisterModel;
import com.nrt.nrt.repository.RegisterLogRepository;

@Service
public class RegisterService {

    @Autowired
    private RegisterLogRepository registerLogRepository;

    public RegisterModel findByEmail(String email) {
        return registerLogRepository.findByEmail(email);
    }

    public String registerUser(RegisterModel user) {
        return registerLogRepository.registerUser(user);
    }

    public RegisterModel findById(Long registerId) {
        return registerLogRepository.findById(registerId);
    }

    public void updateUserBids(RegisterModel user) {
        registerLogRepository.updateUserBids(user); // Call the repository to execute the update query
    }

    public void addBidsToUser(Long registerId, int numberOfBids) {
        RegisterModel user = registerLogRepository.findById(registerId);
        if (user != null) {
            int updatedBids = user.getRemainingBids() + numberOfBids;
            user.setRemainingBids(updatedBids);
            registerLogRepository.updateUserBids(user);
        } else {
            throw new IllegalArgumentException("User not found for ID: " + registerId);
        }
    }


    public RegisterModel getUserById(Long userId) {
        RegisterModel user = registerLogRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found for ID: " + userId);
        }
        return user;
    }
}
