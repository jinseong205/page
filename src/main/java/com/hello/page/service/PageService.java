package com.hello.page.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.hello.page.dao.PageDao;
import com.hello.page.dto.PageDto;

@Service
@RestController
public class PageService {
    
	@Autowired
    private PageDao pageDao;

	@Transactional(readOnly = true)
    public PageDto getPageById(Long id) {
    	
		PageDto pageDto = pageDao.getPageById(id);
		
        // 서브 페이지 가져오기
        if (pageDto != null) {
        	pageDto.setSubpages(pageDao.getSubpages(id));
        }

        // 부모페이지로 breadcrumbs 구성
        pageDao.generateBreadcrumbs(pageDto);

		return pageDto;	
    	
    }


}