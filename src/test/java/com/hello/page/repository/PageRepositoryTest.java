package com.hello.page.repository;

import com.hello.page.entity.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PageRepositoryTest {
    @Autowired
    private PageRepository pageRepository;

    @Test
    void findByParentPageIdTest() {
        Long pageId = 9L;

        List<Page> pageList = pageRepository.findByParentPageId(pageId);
        assertEquals(3, pageList.size());
    }
}