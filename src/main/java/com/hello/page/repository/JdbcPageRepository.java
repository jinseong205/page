package com.hello.page.repository;

import com.hello.page.application.page.domain.Page;
import com.hello.page.application.page.outboundport.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JdbcPageRepository implements PageRepository {
    private static final String SELECT_PAGE_BASE_SQL = """
            SELECT id, title, content, parent_id FROM page""";
    private static final String SELECT_PAGE_BY_ID_SQL = SELECT_PAGE_BASE_SQL + " WHERE id = :id";
    private static final String SELECT_PAGE_BY_PARENT_ID_SQL = SELECT_PAGE_BASE_SQL + " WHERE parent_id = :parentId";
    private static final String SELECT_PAGE_WHERE_ID_IN_SQL = SELECT_PAGE_BASE_SQL + " WHERE id IN (:ids)";

    private final NamedParameterJdbcTemplate template;

    @Override
    public Optional<Page> findById(Long id) {
        try {
            var page = template.queryForObject(
                    SELECT_PAGE_BY_ID_SQL,
                    new MapSqlParameterSource().addValue("id", id),
                    this::pageRowMapper);
            return Optional.ofNullable(page);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Page> findByParentId(Long parentId) {
        return template.query(
                SELECT_PAGE_BY_PARENT_ID_SQL,
                new MapSqlParameterSource().addValue("parentId", parentId),
                this::pageRowMapper);
    }

    @Override
    public List<Page> findAllById(Iterable<Long> ids) {
        return template.query(
                SELECT_PAGE_WHERE_ID_IN_SQL,
                new MapSqlParameterSource().addValue("ids", ids),
                this::pageRowMapper);
    }

    @Override
    public List<Long> findParentPageIds(Long id) {
        return new ArrayList<>();
    }

    private Page pageRowMapper(ResultSet rs, int rowNum) throws SQLException {
        return Page.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .content(rs.getString("content"))
                .parentId(rs.getLong("parent_id"))
                .build();
    }
}
