package com.hello.page.service;

import java.sql.Date;
import java.text.SimpleDateFormat;

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

        
		


        
        long beforeTime = System.currentTimeMillis();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println("10000 depth - 시작 " + dateFormat.format(new Date(beforeTime)));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss.SSS");
        
        //System.out.println("10000 depth - 시작 " + beforeTime);
        
        
        // 부모페이지로 breadcrumbs 구성
        pageDto.setBreadcrumbs(pageDao.getBreadcrumbs(pageDto));
        
        long afterTime = System.currentTimeMillis();
        System.out.println("10000 depth - 종료 " + dateFormat.format(new Date(afterTime)));
        System.out.println("10000 depth - 소요시간 " + (afterTime - beforeTime) + "ms");
        
		return pageDto;	
    	
    }


}