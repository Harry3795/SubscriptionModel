package com.nrt.nrt.repository;

import com.nrt.nrt.model.PlanModel;
import com.nrt.nrt.model.SubscriptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
@Repository
public class SubscriptionRepository {
	@Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    @Autowired
    private PlanRepository planRepository; 
    
    
    
    
 // Add a new subscription
    public void addSubscription(SubscriptionModel subscription) {
        String sql = "INSERT INTO subscriptions (plan_id, register_id, start_date, end_date, remaining_bids) " +
                     "VALUES (:planId, :registerId, :startDate, :endDate, :remainingBids)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("planId", subscription.getPlanId());
        params.addValue("registerId", subscription.getRegisterId());
        params.addValue("startDate", subscription.getStartDate());
        params.addValue("endDate", subscription.getEndDate());
        params.addValue("remainingBids", subscription.getRemainingBids());

        namedParameterJdbcTemplate.update(sql, params);
    }

    // Deduct a bid 
    public void deductBid(Long registerId) {
        String sql = "UPDATE subscriptions SET remaining_bids = remaining_bids - 1 " +
                     "WHERE register_id = :registerId AND remaining_bids > 0 AND end_date > :currentDate " +
                     "ORDER BY end_date ASC OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("registerId", registerId);
        params.addValue("currentDate", java.sql.Date.valueOf(LocalDate.now()));

        // Log for debugging
        System.out.println("Deducting bid for user ID: " + registerId);

        jdbcTemplate.update(sql, params);
    }

 // Retrieve all subscriptions for a customer
    public List<SubscriptionModel> findByRegisterId(Long registerId) {
    	String sql = "SELECT s.*, p.*,p.name as planName FROM subscriptions s JOIN plans p ON s.plan_id = p.id WHERE s.register_id = :registerId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("registerId", registerId);
        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(SubscriptionModel.class));
    }
    
    
    
    public class SubscriptionWithPlanRowMapper implements RowMapper<SubscriptionModel> {
        @Override
        public SubscriptionModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            SubscriptionModel subscription = new SubscriptionModel();
            subscription.setId(rs.getLong("id"));
            subscription.setPlanId(rs.getLong("plan_id"));
            subscription.setRegisterId(rs.getLong("register_id"));
            subscription.setStartDate(rs.getDate("start_date").toLocalDate());
            subscription.setEndDate(rs.getDate("end_date").toLocalDate());
            subscription.setRemainingBids(rs.getInt("remaining_bids"));
            
            // Set the plan name from the join result
            subscription.setPlanName(rs.getString("planName"));
            subscription.setNumberOfBids(rs.getInt("number_of_bids"));
            return subscription;
        }
    }


    public List<SubscriptionModel> findSubscriptionsByUserId(Long userId) {
        String sql = "SELECT * FROM subscriptions WHERE registerId = :registerId";

        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("registerId", userId);

		return namedParameterJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<>(SubscriptionModel.class));
    }

  
    public class SubscriptionRowMapper implements RowMapper<SubscriptionModel> {
        @Override
        public SubscriptionModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            SubscriptionModel subscription = new SubscriptionModel();
            subscription.setId(rs.getLong("id"));
            subscription.setPlanId(rs.getLong("plan_id"));
            subscription.setRegisterId(rs.getLong("register_id"));
            subscription.setStartDate(rs.getDate("start_date").toLocalDate());
            subscription.setEndDate(rs.getDate("end_date").toLocalDate());
            subscription.setRemainingBids(rs.getInt("remaining_bids"));
            return subscription;
        }
    }


    public SubscriptionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to find active subscriptions by register ID
    public List<SubscriptionModel> findActiveSubscriptionByRegisterId(Long registerId) {
        String sql = "SELECT * FROM subscriptions WHERE register_id = :registerId AND end_date > GETDATE()"; // Adjust your SQL as needed
        Map<String, Object> params = Map.of("registerId", registerId);
        
        // Assuming you're using a RowMapper to map the result to SubscriptionModel
        return jdbcTemplate.query(sql, params, new SubscriptionRowMapper());
    }
     
    
    
    public void createSubscription(SubscriptionModel subscription) {
        String sql = "INSERT INTO subscriptions (register_id, plan_id, start_date, end_date) " +
                     "VALUES (:registerId, :planId, :startDate, :endDate)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("registerId", subscription.getRegisterId());
        params.addValue("planId", subscription.getPlanId());
        params.addValue("startDate", LocalDate.now());
        params.addValue("endDate", calculateEndDate(subscription.getPlanId()));

        namedParameterJdbcTemplate.update(sql, params);
    }

    private LocalDate calculateEndDate(Long planId) {
        PlanModel plan = planRepository.findById(planId); // Fetch the plan using PlanRepository
        if (plan != null) {
            return LocalDate.now().plusMonths(plan.getValidityInMonths()); // Use the validity in months from the plan
        } else {
            throw new IllegalArgumentException("Plan not found for ID: " + planId);
        }
    }

           
    }



