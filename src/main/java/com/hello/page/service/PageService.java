package com.hello.page.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.hello.page.dao.PageDao;
import com.hello.page.dto.Page;

@Service
@RestController
public class PageService {
    
	@Autowired
    private PageDao pageDao;

	@Transactional(readOnly = true)
    public Page getPageById(Long id) {
    	return pageDao.getPageById(id);	
    }


}