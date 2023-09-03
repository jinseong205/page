package com.hello.page.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataGenerator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void generateData() {
        for (int i = 1; i <= 9999; i++) {
            String title = generateTitle(i);
            String content = generateContent(i);
            Long parentId = (long)i;

            jdbcTemplate.update("INSERT INTO page (title, content, parent_id) VALUES (?, ?, ?)",
                    title, content, parentId);
        }
    }

    private String generateTitle(int level) {
        StringBuilder title = new StringBuilder("A");
        for (int i = 0; i < level; i++) {
            title.append("A");
        }
        return title.toString();
    }

    private String generateContent(int level) {
        return generateTitle(level); // 이 예제에서는 타이틀과 컨텐트가 동일하게 생성됩니다.
    }
}
