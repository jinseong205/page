package com.hello.page.repository;

import com.hello.page.domain.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PageRepository {
    private static final String SELECT_PAGE_BASE_SQL = "SELECT id, title, content, parent_id FROM page";
    private static final String SELECT_PAGE_BY_ID_SQL = SELECT_PAGE_BASE_SQL + " WHERE id = ?";
    private static final String SELECT_PAGE_BY_PARENT_ID_SQL = SELECT_PAGE_BASE_SQL + " WHERE parent_id = :parentId";
    private static final String SELECT_PAGE_WHERE_ID_IN_SQL = SELECT_PAGE_BASE_SQL + " WHERE id IN (:ids)";
    private static final String SELECT_ANCESTORS_RECURSIVELY = """
            WITH CHILD(ID, PARENT_ID) AS (
              SELECT ID, PARENT_ID
              FROM PAGE
              WHERE ID = :id
              UNION ALL
              SELECT P.ID, P.PARENT_ID
              FROM CHILD C
              INNER JOIN PAGE P ON C.PARENT_ID = P.ID
            )
            SELECT *
            FROM CHILD
            OFFSET 1 ROWS;
            """;

    private final NamedParameterJdbcTemplate template;

    @Autowired
    public PageRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

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
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("ids", ids);
        return template.query(SELECT_PAGE_WHERE_ID_IN_SQL, param, this::pageRowMapper);
    }

    public List<Long> findParentPageIds(Long id) {
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        List<PageInfo> pageInfos = template.query(
                SELECT_ANCESTORS_RECURSIVELY,
                param,
                (rs, rowNum) ->  PageInfo.builder()
                    .id(rs.getLong("id"))
                    .parentId(rs.getLong("parent_id"))
                    .build());

        return pageInfos.stream()
                .map(PageInfo::getId)
                .toList();
    }

    private PageInfo pageRowMapper(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String title = rs.getString("title");
        String content = rs.getString("content");
        Long parentId = rs.getLong("parent_id");

        return PageInfo.builder()
                .id(id)
                .title(title)
                .content(content)
                .parentId(parentId)
                .build();
    }
}
