package subscription.subscription.service;

import java.util.List;

import org.springframework.stereotype.Service;

import subscription.subscription.model.CustomerModel;
import subscription.subscription.model.CustomerSubscriptionModel;
import subscription.subscription.model.SubscriptionPlanModel;

@Service
public interface SubscriptionServiceInterface {

	
	
	 void subscribeToPlan(Long customerId, Long planId);
	    void placeBid(Long customerId, int projectId);
	    List<CustomerSubscriptionModel> getActiveSubscriptions(Long customerId);
	    CustomerModel getCustomerById(Long customerId);
	    List<SubscriptionPlanModel> getAllPlans();
}
