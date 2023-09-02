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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class JdbcPageRepository implements PageRepository {
    private static final String SELECT_PAGE_BASE_SQL = """
            SELECT id, title, content, parent_id FROM page""";
    private static final String SELECT_PAGE_BY_ID_SQL = SELECT_PAGE_BASE_SQL + " WHERE id = :id";
    private static final String SELECT_PAGE_BY_PARENT_ID_SQL = SELECT_PAGE_BASE_SQL + " WHERE parent_id = :parentId";
    private static final String SELECT_PAGE_WHERE_ID_IN_SQL = SELECT_PAGE_BASE_SQL + " WHERE id IN (:ids)";
    private static final String SELECT_ANCESTORS_RECURSIVELY = """
            WITH RECURSIVE child_page(id, parent_id) AS (
              SELECT id, parent_id FROM page
              WHERE id = :id
              UNION ALL
              SELECT page.id, page.parent_id FROM page
              JOIN child_page ON page.id = child_page.parent_id
            )
            SELECT * FROM child_page;
            """;

    private final NamedParameterJdbcTemplate template;

    @Override
    public Optional<Page> findById(Long id) {
        try {
            var page = this.template.queryForObject(
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
        return this.template.query(
                SELECT_PAGE_BY_PARENT_ID_SQL,
                new MapSqlParameterSource().addValue("parentId", parentId),
                this::pageRowMapper);
    }

    @Override
    public List<Page> findAllById(Iterable<Long> ids) {
        return this.template.query(
                SELECT_PAGE_WHERE_ID_IN_SQL,
                new MapSqlParameterSource().addValue("ids", ids),
                this::pageRowMapper);
    }

    @Override
    public List<Long> findParentPageIds(Long id) {
        var parentIdById = this.template.query(
                        SELECT_ANCESTORS_RECURSIVELY,
                        new MapSqlParameterSource().addValue("id", id),
                        (rs, rowNum) -> {
                            var parentId = (Long) rs.getLong("parent_id");
                            if (rs.wasNull()) {
                                parentId = null;
                            }
                            return new IdParentId(rs.getLong("id"), parentId);
                        })
                .stream()
                .collect(Collectors.toMap(
                        IdParentId::id,
                        idParentId -> Optional.ofNullable(idParentId.parentId())));
        var parentIds = new LinkedList<Long>();
        var parentId = parentIdById.get(id);
        while (parentId != null && parentId.isPresent()) {
            parentIds.add(parentId.get());
            parentId = parentIdById.get(parentId.get());
        }
        return parentIds;
    }

    private Page pageRowMapper(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getLong("id");

        var title = rs.getString("title");
        if (rs.wasNull()) {
            title = null;
        }

        var content = rs.getString("content");
        if (rs.wasNull()) {
            content = null;
        }

        var parentId = (Long) rs.getLong("parent_id");
        if (rs.wasNull()) {
            parentId = null;
        }

        return Page.builder()
                .id(id)
                .title(title)
                .content(content)
                .parentId(parentId)
                .build();
    }

    private record IdParentId(Long id, Long parentId) {
    }
}
