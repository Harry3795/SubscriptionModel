package subscription.subscription.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import subscription.subscription.model.CustomerModel;
import subscription.subscription.model.CustomerSubscriptionModel;
import subscription.subscription.model.SubscriptionPlanModel;
import subscription.subscription.service.SubscriptionDAO;

@Repository
public class SubscriptionDAOImpl implements SubscriptionDAO{

	
	 @Autowired
	    private JdbcTemplate jdbcTemplate;

	    @Override
	    public void subscribeToPlan(Long customerId, Long planId) {
	        String planQuery = "SELECT validity_in_months, bids FROM Subs_plans WHERE id = ?";
	        SubscriptionPlanModel plan = jdbcTemplate.queryForObject(planQuery, new Object[]{planId}, 
	            (rs, rowNum) -> {
	                SubscriptionPlanModel p = new SubscriptionPlanModel();
	                p.setValidityInMonths(rs.getInt("validity_in_months"));
	                p.setBids(rs.getInt("bids"));
	                return p;
	            }
	        );
	        
	        String insertSubscription = "INSERT INTO customer_subs (id, plan_id, start_date, end_date, remaining_bids, is_active) " +
	                                    "VALUES (?, ?, ?, ?, ?, 1)";
	        jdbcTemplate.update(insertSubscription, customerId, planId, LocalDate.now(),
	                LocalDate.now().plusMonths(plan.getValidityInMonths()), plan.getBids());
	    }

	    @Override
	    public void placeBid(Long customerId, int projectId) {
	        String activeSubscriptionsQuery = "SELECT id, remaining_bids FROM customer_subs WHERE id = ? AND remaining_bids > 0 AND is_active = 1";
	        List<CustomerSubscriptionModel> subscriptions = jdbcTemplate.query(activeSubscriptionsQuery, new Object[]{customerId}, 
	            (rs, rowNum) -> {
	                CustomerSubscriptionModel subscription = new CustomerSubscriptionModel();
	                subscription.setId(rs.getLong("id"));
	                subscription.setRemainingBids(rs.getInt("remaining_bids"));
	                return subscription;
	            }
	        );
	        
	        if (subscriptions.isEmpty()) {
	            throw new RuntimeException("No active subscription with remaining bids");
	        }

	        CustomerSubscriptionModel subscription = subscriptions.get(0);
	        int remainingBids = subscription.getRemainingBids() - 1;
	        String updateSubscription = "UPDATE customer_subs SET remaining_bids = ? WHERE id = ?";
	        jdbcTemplate.update(updateSubscription, remainingBids, subscription.getId());

	        String insertBidTransaction = "INSERT INTO bid_transactions (id, bid_date, project_id, bid_count) VALUES (?, ?, ?, 1)";
	        jdbcTemplate.update(insertBidTransaction, customerId, LocalDate.now(), projectId);
	    }

	    @Override
	    public List<CustomerSubscriptionModel> getActiveSubscriptions(Long customerId) {
	        String query = "SELECT * FROM customer_subs WHERE id = ? AND is_active = 1";
	        return jdbcTemplate.query(query, new Object[]{customerId}, 
	            (rs, rowNum) -> {
	                CustomerSubscriptionModel subscription = new CustomerSubscriptionModel();
	                subscription.setId(rs.getLong("id"));
	                subscription.setCustomerId(rs.getLong("customer_id"));
	                subscription.setPlanId(rs.getLong("plan_id"));
	                subscription.setStartDate(rs.getDate("start_date").toLocalDate());
	                subscription.setEndDate(rs.getDate("end_date").toLocalDate());
	                subscription.setRemainingBids(rs.getInt("remaining_bids"));
	                subscription.setActive(rs.getBoolean("is_active"));
	                return subscription;
	            }
	        );
	    }

	    @Override
	    public List<SubscriptionPlanModel> getAllPlans() {
	        String query = "SELECT * FROM Subs_plans";
	        return jdbcTemplate.query(query, 
	            (rs, rowNum) -> {
	                SubscriptionPlanModel plan = new SubscriptionPlanModel();
	                plan.setId(rs.getLong("id"));
	                plan.setName(rs.getString("name"));
	                plan.setValidityInMonths(rs.getInt("validity_in_months"));
	                plan.setBids(rs.getInt("bids"));
	                plan.setAmount(rs.getDouble("amount"));
	                return plan;
	            }
	        );
	    }

		@Override
		public CustomerModel getCustomerById(Long customerId) {
			// TODO Auto-generated method stub
			return null;
		}
	}