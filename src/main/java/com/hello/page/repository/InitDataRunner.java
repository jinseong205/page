package com.hello.page.repository;

import com.hello.page.domain.PageInfo;
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
    private static final String INSERT_PAGE_SQL = "INSERT INTO page (title, content, parent_id) VALUES (:title, :content, :parentId)";
    private static final String ERROR_MESSAGE = "초기 데이터 생성에 실패했습니다.";
    private static final List<String> tags = new ArrayList<>(List.of("A", "B", "C"));

    private final NamedParameterJdbcTemplate template;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    private final int finalLevel = 5;

    @PostConstruct
    public void initData() {
        List<TagId> preLevelIds = tags.stream()
                .map(tag -> {
                    var id = this.createRootPage(tag);
                    return new TagId(tag, id);
                })
                .toList();

        for (int level = 2; level <= finalLevel; level++) {
            List<TagId> nowLevelIds = new LinkedList<>();
            for (TagId tagId : preLevelIds) {
                String parentTag = tagId.tag;
                Long parentId = tagId.id;

                for (String childTag : tags) {
                    String tag = parentTag + childTag;
                    Long id = this.createPage(level, tag, parentId);
                    nowLevelIds.add(new TagId(tag, id));
                }
            }
            preLevelIds = nowLevelIds;
        }
    }

    private Long createRootPage(String tag) {
        return this.createPage(1, tag, null);
    }

    private Long createPage(int level, String tag, Long parentId) {
        var page = PageInfo.builder()
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

    private record TagId(String tag, Long id) {}
}