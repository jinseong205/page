package com.hello.page.repository;

import com.hello.page.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PageRepositoryImpl implements PageRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Page> rowMapper;

    @Autowired
    public PageRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        rowMapper = BeanPropertyRowMapper.newInstance(Page.class);
    }

    @Override
    public List<Page> findByParentPageId(Long parentPageId) {
        String sql = "SELECT * FROM page WHERE parent_page_id = ?";
        return jdbcTemplate.query(sql, rowMapper, parentPageId);
    }

    @Override
    public List<Page> findAllByPageId(Long pageId) {
        String sql = """
                WITH CHILD(PAGE_ID, TITLE, CONTENT, PARENT_PAGE_ID) AS (
                  SELECT PAGE_ID, TITLE, CONTENT, PARENT_PAGE_ID
                  FROM PAGE
                  WHERE PAGE_ID = ?
                  UNION ALL
                  SELECT P.PAGE_ID, P.TITLE, P.CONTENT, P.PARENT_PAGE_ID
                  FROM CHILD C
                  INNER JOIN PAGE P ON C.PARENT_PAGE_ID = P.PAGE_ID
                )
                SELECT *
                FROM CHILD;
                """;

        return jdbcTemplate.query(sql, rowMapper, pageId);
    }
}
