package subscription.subscription.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import subscription.subscription.model.CustomerModel;
import subscription.subscription.model.CustomerSubscriptionModel;
import subscription.subscription.model.SubscriptionPlanModel;

@Service
public class SubscriptionServiceImpl  implements SubscriptionServiceInterface  {
	

	 @Autowired
	    private SubscriptionDAO subscriptionDAO;

	    @Override
	    public void subscribeToPlan(Long customerId, Long planId) {
	        subscriptionDAO.subscribeToPlan(customerId, planId);
	    }

	    @Override
	    public void placeBid(Long customerId, int projectId) {
	        subscriptionDAO.placeBid(customerId, projectId);
	    }

	    @Override
	    public List<CustomerSubscriptionModel> getActiveSubscriptions(Long customerId) {
	        return subscriptionDAO.getActiveSubscriptions(customerId);
	    }

	    @Override
	    public CustomerModel getCustomerById(Long customerId) {
	        return subscriptionDAO.getCustomerById(customerId);
	    }

	    @Override
	    public List<SubscriptionPlanModel> getAllPlans() {
	        return subscriptionDAO.getAllPlans();
	    }

}
 