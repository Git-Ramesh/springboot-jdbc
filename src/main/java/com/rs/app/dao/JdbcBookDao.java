package com.rs.app.dao;

import java.util.List;
import java.util.Optional;

import com.rs.app.bo.JdbcBookBo;

public interface JdbcBookDao {
	JdbcBookBo save(JdbcBookBo jdbcBookBo);

	List<JdbcBookBo> findAll();

	Optional<JdbcBookBo> findById(long id);

}
