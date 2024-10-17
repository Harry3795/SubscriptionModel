package subscription.subscription.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import subscription.subscription.model.CustomerSubscriptionModel;
import subscription.subscription.model.SubscriptionPlanModel;
import subscription.subscription.service.SubscriptionServiceImpl;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {


	
	    @Autowired
	    private SubscriptionServiceImpl subscriptionService;

	    @PostMapping("/subscribe")
	    public ResponseEntity<String> subscribe(@RequestParam Long customerId, @RequestParam Long planId) {
	        subscriptionService.subscribeToPlan(customerId, planId);
	        return ResponseEntity.ok("Subscribed successfully!");
	    }

	    @PostMapping("/bid")
	    public ResponseEntity<String> placeBid(@RequestParam Long customerId, @RequestParam int projectId) {
	        subscriptionService.placeBid(customerId, projectId);
	        return ResponseEntity.ok("Bid placed successfully!");
	    }

	    @GetMapping("/plans")
	    public ResponseEntity<List<SubscriptionPlanModel>> getAllPlans() {
	        return ResponseEntity.ok(subscriptionService.getAllPlans());
	    }

	    @GetMapping("/activeSubscriptions")
	    public ResponseEntity<List<CustomerSubscriptionModel>> getActiveSubscriptions(@RequestParam Long customerId) {
	        return ResponseEntity.ok(subscriptionService.getActiveSubscriptions(customerId));
	    }
	}

