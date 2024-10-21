package com.nrt.nrt.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nrt.nrt.model.PlanModel;
import com.nrt.nrt.model.RegisterModel;
import com.nrt.nrt.model.SubscriptionModel;
import com.nrt.nrt.repository.PlanRepository;
import com.nrt.nrt.service.PlanService;
import com.nrt.nrt.service.RegisterService;
import com.nrt.nrt.service.SubscriptionService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {
	@Autowired
	PlanRepository planRepository;
	@Autowired
    private RegisterService registerService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private PlanService planService;

        
    

    // Display available plans
    @GetMapping("/plans")
    public ModelAndView showPlans() {
        List<PlanModel> plans = subscriptionService.getAllPlans();
        ModelAndView modelAndView = new ModelAndView("plans");
        modelAndView.addObject("plans", plans);
        return modelAndView;
    }

    
    @PostMapping("/subscribe")
    public ModelAndView subscribeToPlan(@RequestParam Long registerId, @RequestParam Long planId, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        
        RegisterModel user = registerService.findById(registerId);
        
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid user.");
            modelAndView.setViewName("redirect:/subscriptions/plans");
            return modelAndView;
        }
        
       
        
        PlanModel plan = planRepository.findById(planId);
        if (plan == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid plan.");
            modelAndView.setViewName("redirect:/dashboard");
            return modelAndView;
        }

        if (user.getRemainingBids() < plan.getNumberOfBids()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Not enough bids to subscribe to this plan.");
            modelAndView.setViewName("redirect:/dashboard");
            return modelAndView;
        }

        subscriptionService.subscribeToPlan(registerId, planId);
        user.setRemainingBids(user.getRemainingBids() - 1);
        registerService.updateUserBids(user); // Ensure you have a method to update user details

        redirectAttributes.addFlashAttribute("successMessage", "Subscribed successfully.");
        modelAndView.setViewName("redirect:/dashboard");
        return modelAndView;
    }

    @GetMapping("/buy-bids")
    public ModelAndView showBuyBidsForm(HttpSession session) {
    
    	ModelAndView modelAndView = new ModelAndView("buy-bids");
        Long registerId = (Long) session.getAttribute("registerId");

        if (registerId == null) {
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        List<PlanModel> plans = planService.getAllPlans(); // Fetch all plans
        modelAndView.addObject("plans", plans);
        modelAndView.addObject("user", registerService.findById(registerId));
        return modelAndView;

    }


    @PostMapping("/purchase-bids")
    public ModelAndView purchaseBids(@RequestParam Long registerId, @RequestParam Long planId, RedirectAttributes redirectAttributes) {
        System.out.println("Register ID: " + registerId);
        System.out.println("Plan ID: " + planId);

    	ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");

        try {
            // Fetch the plan details
            PlanModel plan = planService.findById(planId);
            if (plan == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Invalid plan selected.");
                return modelAndView;
            }

            // Update the user's remaining bids (adding the bids from the selected plan)
            registerService.addBidsToUser(registerId, plan.getNumberOfBids());

            // Add the subscription for the selected plan
            subscriptionService.subscribeToPlan(registerId, planId);

            // Success message
            redirectAttributes.addFlashAttribute("successMessage", "Bids purchased and subscription added successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error during purchase: " + e.getMessage());
        }

        return modelAndView;
    }



    // View subscriptions for a customer
    @GetMapping("/customer/{registerId}")
    public ModelAndView getSubscriptions(@PathVariable Long registerId) {
        List<SubscriptionModel> subscriptions = subscriptionService.getSubscriptionsByRegisterId(registerId);
        ModelAndView modelAndView = new ModelAndView("subscriptions");
        modelAndView.addObject("subscriptions", subscriptions);
        return modelAndView;
    }

    @GetMapping("/subscriptions/buy-bids")
    public String showBuyBidsPage(Model model, HttpSession session) {
        Long registerId = (Long) session.getAttribute("registerId"); 
        RegisterModel user = registerService.findById(registerId);
        List<PlanModel> plans = planService.getAllPlans(); 

       
        model.addAttribute("user", user);
        model.addAttribute("plans", plans); 
        return "buy-bids"; 
    }
   

  

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
  

}
