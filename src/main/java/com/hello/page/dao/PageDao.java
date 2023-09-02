package com.hello.page.dao;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.hello.page.dto.PageDto;

@Repository
public class PageDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // 메인 페이지 정보 가져오기
    public PageDto getPageById(Long id) {
        String sql = "SELECT * FROM page WHERE id = ?";
        
        RowMapper<PageDto> rowMapper = new BeanPropertyRowMapper<>(PageDto.class);
        PageDto pageDto = jdbcTemplate.queryForObject(sql, rowMapper, id);

        return pageDto;
    }

    // 서브페이지 정보 가져오기
    public List<PageDto> getSubpages(Long parentId) {
        String sql = "SELECT * FROM page WHERE parent_id = ?";
        
        RowMapper<PageDto> rowMapper = new BeanPropertyRowMapper<>(PageDto.class);
        List<PageDto> subpages = jdbcTemplate.query(sql, rowMapper, parentId);

        return subpages;
    }

    // 페이지의 breadcrumbs 생성
    public void generateBreadcrumbs(PageDto page) {
        
    	Long parentId = page.getParent_id();
        Deque<String> breadcrumbs = new LinkedList<>();
        
        while (parentId != null) {
            PageDto parentPage = getPageById(parentId);
            if (parentPage != null) {
            	breadcrumbs.addFirst(parentPage.getTitle());
                parentId = parentPage.getParent_id();
            }
        }

        page.setBreadcrumbs((List)breadcrumbs);
    }
}
