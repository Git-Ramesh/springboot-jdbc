package com.rs.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.rs.app.bo.JdbcBookBo;
import com.rs.app.dao.JdbcBookDao;
import com.rs.app.dto.JdbcBookDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JdbcBookServiceImpl implements JdbcBookService {
	@Autowired
	private JdbcBookDao jdbcBookDao;
	@Autowired
	private JdbcBookServiceHelper bookServiceHelper;
	@Autowired
	private TransactionManager txMgr;

	@Override
	public JdbcBookDto save(JdbcBookDto jdbcBookDto) {
		JdbcBookBo jdbcBookBo = new JdbcBookBo();
		BeanUtils.copyProperties(jdbcBookDto, jdbcBookBo);
		JdbcBookBo savedJdbcBookBo = jdbcBookDao.save(jdbcBookBo);
		JdbcBookDto savedJdbcBookDto = new JdbcBookDto();
		BeanUtils.copyProperties(savedJdbcBookBo, savedJdbcBookDto);
		return savedJdbcBookDto;
	}

	@Override
	public List<JdbcBookDto> getAll() {
		return jdbcBookDao.findAll().parallelStream().map(JdbcBookDto::new).collect(Collectors.toList());
	}

	@Override
	public JdbcBookDto get(long id) {
		Optional<JdbcBookBo> optionalJdbcUserBo = jdbcBookDao.findById(id);

		if (optionalJdbcUserBo.isPresent()) {
			return new JdbcBookDto(optionalJdbcUserBo.get());
		} else {
			throw new RuntimeException("No Results Found!");
		}
	}

	// Note: If transaction is nested then if main transaction rollback then nested transaction gets rollbacks
	// If nested transaction rollbacks and main transaction handles that exception main transaction won't rollbacks otherwise main transaction also rollbacks. 
	@Override
	@Transactional
	public void registerBooks(JdbcBookDto o1, JdbcBookDto o2) {
		log.info(txMgr.getClass().getCanonicalName());
		save(o1);
		try {
			bookServiceHelper.register(o2);
		} catch (Exception e) {
			
		}
//		if(true) {
//			throw new RuntimeException("Blah.....");
//		}
	}

}
