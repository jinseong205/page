package com.hello.page.dao;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hello.page.dto.Page;

@Repository
public class PageDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // 메인 페이지 정보 가져오기
    public Page getPageById(Long id) {
        String sql = "SELECT * FROM page WHERE id = ?";
        
        RowMapper<Page> rowMapper = new BeanPropertyRowMapper<>(Page.class);
        Page page = jdbcTemplate.queryForObject(sql, rowMapper, id);

        // 서브 페이지 가져오기
        if (page != null) {
            page.setSubpages(getSubpages(id));
        }

        // 부모페이지로 breadcrumbs 구성
        generateBreadcrumbs(page);

        return page;
    }

    // 서브페이지 정보 가져오기
    public List<Page> getSubpages(Long parentId) {
        String sql = "SELECT * FROM page WHERE parent_id = ?";
        
        RowMapper<Page> rowMapper = new BeanPropertyRowMapper<>(Page.class);
        List<Page> subpages = jdbcTemplate.query(sql, rowMapper, parentId);

        return subpages;
    }

    // 페이지의 breadcrumbs 생성
    private void generateBreadcrumbs(Page page) {
        
    	Long parentId = page.getParent_id();
        Deque<String> breadcrumbs = new LinkedList<>();
        
        while (parentId != null) {
            Page parentPage = getPageById(parentId);
            if (parentPage != null) {
            	breadcrumbs.addFirst(parentPage.getTitle());
                parentId = parentPage.getParent_id();
            }
        }

        page.setBreadcrumbs((List)breadcrumbs);
    }
}
