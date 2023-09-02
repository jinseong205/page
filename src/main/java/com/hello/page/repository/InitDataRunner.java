package com.hello.page.repository;

import com.hello.page.application.page.domain.Page;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class InitDataRunner {
    private static final String INSERT_PAGE_SQL = """
            INSERT INTO page (title, content, parent_id) VALUES (:title, :content, :parentId)""";
    private static final String ERROR_MESSAGE = "초기 데이터 생성에 실패했습니다.";
    private static final List<String> tags = new ArrayList<>(List.of("A", "B", "C"));

    private final NamedParameterJdbcTemplate template;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    @PostConstruct
    public void initData() {
        var level1PageTagIds = tags.stream()
                .map(tag -> {
                    var id = this.createRootPage(tag);
                    return new TagId(tag, id);
                })
                .toList();

        var level2PageTagIds = new LinkedList<TagId>();
        for (var tagId : level1PageTagIds) {
            var parentTag = tagId.tag();
            var parentId = tagId.id();
            for (var childTag : tags) {
                var tag = parentTag + childTag;
                var id = this.createPage(2, tag, parentId);
                level2PageTagIds.add(new TagId(tag, id));
            }
        }

        var level3PageTagIds = new LinkedList<TagId>();
        for (var tagId : level2PageTagIds) {
            var parentTag = tagId.tag();
            var parentId = tagId.id();
            for (var childTag : tags) {
                var tag = parentTag + childTag;
                var id = this.createPage(3, tag, parentId);
                level3PageTagIds.add(new TagId(tag, id));
            }
        }

        for (var tagId : level3PageTagIds) {
            var parentTag = tagId.tag();
            var parentId = tagId.id();
            for (var childTag : tags) {
                var tag = parentTag + childTag;
                this.createPage(4, tag, parentId);
            }
        }
    }

    private Long createRootPage(String tag) {
        return this.createPage(1, tag, null);
    }

    private Long createPage(int level, String tag, Long parentId) {
        var page = Page.builder()
                .title(tag)
                .content("Hello Level" + level + " " + tag)
                .parentId(parentId)
                .build();
        this.template.update(
                INSERT_PAGE_SQL,
                new BeanPropertySqlParameterSource(page),
                this.keyHolder);
        var key = this.keyHolder.getKey();
        if (!(key instanceof Long id)) {
            throw new RuntimeException(ERROR_MESSAGE);
        }
        return id;
    }

    private record TagId(String tag, Long id) {
    }
}
