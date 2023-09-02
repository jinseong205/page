package com.hello.page.repository;

import com.hello.page.domain.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PageRepository {
    private static final String SELECT_PAGE_BASE_SQL = "SELECT id, title, content, parent_id FROM page";
    private static final String SELECT_PAGE_BY_ID_SQL = SELECT_PAGE_BASE_SQL + " WHERE id = :id";
    private static final String SELECT_PAGE_BY_PARENT_ID_SQL = SELECT_PAGE_BASE_SQL + " WHERE parent_id = :parentId";
    private static final String SELECT_PAGE_WHERE_ID_IN_SQL = SELECT_PAGE_BASE_SQL + " WHERE id IN (:ids)";
    private static final String SELECT_ANCESTORS_RECURSIVELY = """
            WITH RECURSIVE child(id, parent_id) AS (
              SELECT id, parent_id
              FROM page
              WHERE id = :id
              UNION ALL
              SELECT p.id, p.parent_id
              FROM child c
              INNER JOIN page p ON c.parent_id = p.id
            )
            SELECT *
            FROM child
            OFFSET 1 ROWS;
            """;

    private final NamedParameterJdbcTemplate template;

    public Optional<PageInfo> findById(Long id) {
        try {
            MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
            PageInfo pageInfo = template.queryForObject(SELECT_PAGE_BY_ID_SQL, param, this::pageRowMapper);

            return Optional.ofNullable(pageInfo);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<PageInfo> findByParentId(Long parentId) {
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("parentId", parentId);
        return template.query(SELECT_PAGE_BY_PARENT_ID_SQL, param, this::pageRowMapper);
    }

    public List<PageInfo> findAllById(Iterable<Long> ids) {
        List<PageInfo> pageInfos = new ArrayList<>();

        if (ids.iterator().hasNext()) {
            MapSqlParameterSource param = new MapSqlParameterSource().addValue("ids", ids);
            pageInfos = template.query(SELECT_PAGE_WHERE_ID_IN_SQL, param, this::pageRowMapper);
        }

        return pageInfos;
    }

    public List<Long> findParentPageIds(Long id) {
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        return template.query(
                SELECT_ANCESTORS_RECURSIVELY,
                param,
                (rs, rowNum) ->  rs.getLong("id"));
    }

    private PageInfo pageRowMapper(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String title = rs.getString("title");
        if (rs.wasNull()) {
            title = null;
        }
        String content = rs.getString("content");
        if (rs.wasNull()) {
            content = null;
        }
        Long parentId = rs.getLong("parent_id");
        if (rs.wasNull()) {
            parentId = null;
        }

        return PageInfo.builder()
                .id(id)
                .title(title)
                .content(content)
                .parentId(parentId)
                .build();
    }
}
