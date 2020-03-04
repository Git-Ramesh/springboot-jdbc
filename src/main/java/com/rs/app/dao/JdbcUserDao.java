package com.rs.app.dao;

import java.util.List;

import com.rs.app.bo.JdbcUserBo;

public interface JdbcUserDao {
	JdbcUserBo save(JdbcUserBo jdbcUserBo);
	JdbcUserBo get(long id);
	List<JdbcUserBo> getAll();
	JdbcUserBo update(JdbcUserBo jdbcUserBo);
}
