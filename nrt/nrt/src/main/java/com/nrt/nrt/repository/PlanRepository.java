package com.nrt.nrt.repository;

import com.nrt.nrt.model.PlanModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlanRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public PlanModel findById(Long planId) {
        String sql = "SELECT * FROM plans WHERE id = :planId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("planId", planId);
        List<PlanModel> plans = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(PlanModel.class));
        return plans.isEmpty() ? null : plans.get(0);
    }

    public List<PlanModel> findAll() {
        String sql = "SELECT * FROM plans";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PlanModel.class));
    }
    
 
}
