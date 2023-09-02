package com.hello.page.repository;

import com.hello.page.domain.PageInfo;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("id를 기준으로 페이지를 조회한다.")
    void findByIdTest() {
        PageInfo pageInfo = pageRepository.findById(1L).get();
        assertNotNull(pageInfo);
    }

    @Test
    @DisplayName("id 목록을 기준으로 서브 페이지 목록을 조회한다.")
    void findByParentIdTest() {
        List<PageInfo> pageInfos = pageRepository.findByParentId(9L);
        assertEquals(3, pageInfos.size());
    }

    @Test
    @DisplayName("id 목록을 기준으로 페이지 목록을 조회한다.")
    void findAllById() {
        List<Long> ids = pageRepository.findParentPageIds(9L);
        assertEquals(9, ids.size());

        List<PageInfo> pageInfos = pageRepository.findAllById(ids);
        assertEquals(9, pageInfos.size());
    }

    @Test
    @DisplayName("pageId 를 기준으로 root 페이지까지의 id를 순서대로 조회한다.")
    void findParentPageIdsTest() {
        List<Long> ids = pageRepository.findParentPageIds(10L);
        assertEquals(9, ids.size());
    }
}