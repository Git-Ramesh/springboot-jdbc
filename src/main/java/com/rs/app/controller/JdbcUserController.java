package com.rs.app.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rs.app.dto.JdbcUserDto;
import com.rs.app.service.JdbcUserService;

@RestController
@RequestMapping("/jdbc-user")
public class JdbcUserController {
	@Autowired
	private JdbcUserService jdbcUserService;

	@PostMapping
	public ResponseEntity<JdbcUserDto> add(@RequestBody JdbcUserDto jdbcUserDto) {
		JdbcUserDto savedJdbcUser = jdbcUserService.addJdbcUser(jdbcUserDto);
		return ResponseEntity.ok(savedJdbcUser);
	}

	@GetMapping("/{id}")
	public ResponseEntity<JdbcUserDto> get(@PathVariable("id") long id) {
		JdbcUserDto jdbcUser = jdbcUserService.getJdbcUser(id);
		return ResponseEntity.ok(jdbcUser);
	}

	@GetMapping("/all")
	public ResponseEntity<List<JdbcUserDto>> get() {
		List<JdbcUserDto> allJdbcUsers = jdbcUserService.getAllJdbcUsers();
		return ResponseEntity.ok(allJdbcUsers);
	}
	
	@PutMapping
	public ResponseEntity<JdbcUserDto> update(@RequestBody JdbcUserDto jdbcUserDto) {
		JdbcUserDto updateJdbcUser = jdbcUserService.updateJdbcUser(jdbcUserDto);
		return ResponseEntity.ok(updateJdbcUser);
	}
	
	@GetMapping("/transaction/{id}")
	public void transactionTest(@PathVariable("id") long id) throws IOException {
		jdbcUserService.transactionTest(id);
	}
}
