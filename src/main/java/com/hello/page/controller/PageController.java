package com.hello.page.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hello.page.dto.Page;
import com.hello.page.service.PageService;

@RestController
public class PageController {

	@Autowired
	private PageService pageService;
	
	
	@GetMapping("/page/{id}")
    public Page getPageById(@PathVariable Long id) {
    	return pageService.getPageById(id);	
    }

}
