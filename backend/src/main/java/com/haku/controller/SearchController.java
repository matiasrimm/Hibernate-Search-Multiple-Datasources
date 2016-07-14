package com.haku.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haku.service.SearchService;

@RestController
public class SearchController {

	@Autowired
	SearchService searchService;	
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/")	
	public ResponseEntity<List<Object>> findUmagebeanno(@RequestParam("s") String search) {
		
		List<Object> list = searchService.wrapSearchQueries(search);
		
		return new ResponseEntity<List<Object>>(list, HttpStatus.OK);		
	}
				
	
	// for indexing
	@RequestMapping("/index")	
	public String index() {
		
		searchService.reIndex();
		
		return "indeksoitu";		
	}
	
}
