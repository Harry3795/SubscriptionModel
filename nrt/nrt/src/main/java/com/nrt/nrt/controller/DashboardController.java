package com.nrt.nrt.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nrt.nrt.model.PlanModel;
import com.nrt.nrt.model.ProjectModel;
import com.nrt.nrt.model.RegisterModel;
import com.nrt.nrt.model.SubscriptionModel;
import com.nrt.nrt.repository.SubscriptionRepository;
import com.nrt.nrt.service.ProjectService;
import com.nrt.nrt.service.RegisterService;
import com.nrt.nrt.service.SubscriptionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {
    @Autowired
    private RegisterService registerService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    ProjectService projectService;
    
    @GetMapping("/dashboard")
    public ModelAndView showDashboard(HttpSession session) {
      
        Long registerId = (Long) session.getAttribute("registerId");
        RegisterModel user = registerService.findById(registerId);
         
		List<ProjectModel> projects = projectService.getAllProjects();
        List<SubscriptionModel> subscriptions = subscriptionService.getSubscriptionsByRegisterId(registerId);
        
        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("user", user);
        modelAndView.addObject("subscriptions", subscriptions);
        if (registerId == null) {
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        // Fetch user and subscriptions
        
//        List<SubscriptionModel> subscriptions = subscriptionRepository.findByRegisterId(registerId);
        List<SubscriptionModel> activeSubscriptions = subscriptionRepository.findActiveSubscriptionByRegisterId(registerId); // Use the new method
        List<PlanModel> availablePlans = subscriptionService.getAllPlans();
        
        
        Set<String> uniquePlanNames = new HashSet<>();
        for (SubscriptionModel subscription : subscriptions) {
            uniquePlanNames.add(subscription.getPlanName());
        }
        // Check if there are any active subscriptions
        SubscriptionModel activeSubscription = null;
        if (!activeSubscriptions.isEmpty()) {
            activeSubscription = activeSubscriptions.get(0); 
        }

        // Add data to the model
        modelAndView.addObject("uniquePlanNames", uniquePlanNames);
        modelAndView.addObject("projects", projects);
        modelAndView.addObject("plans", availablePlans);
        modelAndView.addObject("user", user);
        modelAndView.addObject("subscriptions", subscriptions);
        modelAndView.addObject("activeSubscription", activeSubscription); 
        modelAndView.addObject("isNewUser", subscriptions.isEmpty());

        return modelAndView;
    }
}
