package com.rs.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rs.app.dto.JdbcBookDto;
import com.rs.app.service.JdbcBookService;

@RestController
@RequestMapping("/jdbc-book")
public class JdbcBookController {
	@Autowired
	private JdbcBookService jdbcBookService;

	@GetMapping(value = "/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<JdbcBookDto> get(@PathVariable("id") long id) {
		return ResponseEntity.ok(jdbcBookService.get(id));
	}

	@GetMapping
	public ResponseEntity<List<JdbcBookDto>> get() {
		return ResponseEntity.ok(jdbcBookService.getAll());
	}

	@PostMapping
	public ResponseEntity<JdbcBookDto> save(@RequestBody JdbcBookDto jdbcBookDto) {
		return ResponseEntity.ok(jdbcBookService.save(jdbcBookDto));
	}
}
