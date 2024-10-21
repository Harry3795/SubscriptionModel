package com.nrt.nrt.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nrt.nrt.model.ProjectModel;

@Repository
public class ProjectRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<ProjectModel> findAll() {
        String sql = "SELECT * FROM projects";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProjectModel.class));
    }

    public ProjectModel findById(Long projectId) {
        String sql = "SELECT * FROM projects WHERE project_id = :projectId";
        MapSqlParameterSource params = new MapSqlParameterSource("projectId", projectId);
        return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(ProjectModel.class));
    }

	
}
