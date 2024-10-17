package subscription.subscription.service;

import java.util.List;

import subscription.subscription.model.CustomerModel;
import subscription.subscription.model.CustomerSubscriptionModel;
import subscription.subscription.model.SubscriptionPlanModel;

public interface SubscriptionDAO {
	void subscribeToPlan(Long customerId, Long planId);
    void placeBid(Long customerId, int projectId);
    List<CustomerSubscriptionModel> getActiveSubscriptions(Long Id);
    CustomerModel getCustomerById(Long Id);  // Additional method if needed
    List<SubscriptionPlanModel> getAllPlans();       // Additional method to list plans
}