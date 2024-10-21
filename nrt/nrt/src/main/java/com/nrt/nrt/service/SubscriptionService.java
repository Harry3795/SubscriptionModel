package com.nrt.nrt.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nrt.nrt.model.PlanModel;
import com.nrt.nrt.model.RegisterModel;
import com.nrt.nrt.model.SubscriptionModel;
import com.nrt.nrt.repository.PlanRepository;
import com.nrt.nrt.repository.RegisterLogRepository;
import com.nrt.nrt.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private RegisterLogRepository registerLogRepository;

    // Subscribe to a plan
    public void subscribeToPlan(Long registerId, Long planId) {
        PlanModel plan = planRepository.findById(planId);
        if (plan == null) {
            throw new IllegalArgumentException("Invalid plan ID");
        }

        RegisterModel customer = registerLogRepository.findById(registerId);
        if (customer == null) {
            throw new IllegalArgumentException("Invalid register ID: " + registerId);
        }


        SubscriptionModel subscription = new SubscriptionModel();
        subscription.setPlanId(planId);
        subscription.setRegisterId(registerId);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusMonths(plan.getValidityInMonths()));
        subscription.setRemainingBids(plan.getNumberOfBids());

        subscriptionRepository.addSubscription(subscription);
    }

    // Place a bid by deducting a bid count
    
    @Transactional
    public void placeBid(Long registerId) {
        RegisterModel user = registerLogRepository.findById(registerId);
        if (user == null || user.getRemainingBids() <= 0) {
            throw new IllegalArgumentException("Insufficient bids.");
        }

        // Deduct 1 bid from the subscription with the closest end date
        subscriptionRepository.deductBid(registerId);

        // Update user's remaining bids, assuming user object tracks total bids
        user.setRemainingBids(user.getRemainingBids() - 1);
        registerLogRepository.updateUserBids(user);
    }



    // Get all subscriptions for a customer
    public List<SubscriptionModel> getSubscriptionsByRegisterId(Long registerId) {
        return subscriptionRepository.findByRegisterId(registerId);
    }

    // Get all available plans
    public List<PlanModel> getAllPlans() {
        return planRepository.findAll();
    }
    
    
    public RegisterModel getUserById(Long userId) {
        RegisterModel user = registerLogRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found for ID: " + userId);
        }
        return user;
    }

    // Method to get the subscriptions for the user
    public List<SubscriptionModel> getSubscriptionsForUser(Long userId) {
        return subscriptionRepository.findSubscriptionsByUserId(userId);
    }



    public List<SubscriptionModel> findByRegisterId(Long registerId) {
        return subscriptionRepository.findByRegisterId(registerId);
    }
    
    
    
    public void purchaseBids(Long registerId, int numberOfBids) {
        RegisterModel user = registerLogRepository.findById(registerId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user ID.");
        }

        // Update user's remaining bids
        int currentBids = user.getRemainingBids();
        user.setRemainingBids(currentBids + numberOfBids);

        // Save the updated user back to the database
        registerLogRepository.updateUserBids(user);
    }

     // Your data access layer

    public List<SubscriptionModel> getActiveSubscription(Long registerId) {
        // Fetch active subscription from the repository by registerId
        // This is a sample query, you'll need to customize it based on your schema

        return subscriptionRepository.findActiveSubscriptionByRegisterId(registerId);
    }
  
    
    public void addSubscription(Long registerId, Long planId) {
        SubscriptionModel subscription = new SubscriptionModel();
        subscription.setRegisterId(registerId); // Using registerId now
        subscription.setPlanId(planId);
        
        subscriptionRepository.createSubscription(subscription);
    }

    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  
