package com.nrt.nrt.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nrt.nrt.model.RegisterModel;



@Repository
public class RegisterLogRepository {
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public String registerUser(RegisterModel user) {

		String sql = "INSERT INTO register_model (email,password)"
				+ " VALUES (:email,:password)";

		MapSqlParameterSource paramap = new MapSqlParameterSource();

		
		paramap.addValue("email", user.getEmail());
		paramap.addValue("password", user.getPassword());

		int i = namedParameterJdbcTemplate.update(sql, paramap);
		if (i == 1)
			return "Record Inserted";
		else
			return "Record cannot be inserted";
	}


	public RegisterModel findByEmail(String email) {
	    String sql = "SELECT * FROM register_model WHERE email = :email";

	    MapSqlParameterSource paramap = new MapSqlParameterSource();
	    paramap.addValue("email", email);
	    
	    //The query method is used with a BeanPropertyRowMapper to map the result set to a list of RegisterModel objects.
	    
	    List<RegisterModel> list = namedParameterJdbcTemplate.query(sql, paramap, new BeanPropertyRowMapper<>(RegisterModel.class));
	    
	    if (!list.isEmpty()) {
	        return list.get(0); // Return the first found RegisterModel object
	    } else {
	        return null; // Return null if no RegisterModel is found
	    }
	}
	
	 public RegisterModel findById(Long registerId) {
		    String sql = "SELECT * FROM register_model WHERE register_id = :registerId";
		    MapSqlParameterSource params = new MapSqlParameterSource();
		    params.addValue("registerId", registerId);
		    List<RegisterModel> users = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(RegisterModel.class));
		    return users.isEmpty() ? null : users.get(0);
		}
	
	


	 public void updateUserBids(RegisterModel user) {
		    String sql = "UPDATE register_model SET remaining_bids = :remainingBids WHERE register_id = :registerId";
		    MapSqlParameterSource params = new MapSqlParameterSource();
		    params.addValue("remainingBids", user.getRemainingBids());
		    params.addValue("registerId", user.getRegisterId());
		    namedParameterJdbcTemplate.update(sql, params);
		}


	 public void updateUserBids(Long registerId, int remainingBids) {
		    String sql = "UPDATE register_model SET remaining_bids = :remainingBids WHERE register_id = :registerId";
		    MapSqlParameterSource params = new MapSqlParameterSource();
		    params.addValue("remainingBids", remainingBids);
		    params.addValue("registerId", registerId);
		    namedParameterJdbcTemplate.update(sql, params);
		}

	

	
	

}
