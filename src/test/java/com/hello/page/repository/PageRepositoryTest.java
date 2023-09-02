package com.hello.page.repository;

import com.hello.page.domain.PageInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PageRepositoryTest {
    @Autowired
    private PageRepository pageRepository;

    @Test
    @DisplayName("id를 기준으로 페이지를 조회한다.")
    void findByIdTest() {
        Optional<PageInfo> pageInfoOptional = pageRepository.findById(1L);
        assertThat(pageInfoOptional.isPresent()).isTrue();
    }

    @Test
    @DisplayName("id 를 기준으로 서브 페이지 목록을 조회한다.")
    void findByParentIdTest() {
        List<PageInfo> pageInfos = pageRepository.findByParentId(91L);
        assertThat(pageInfos.size() == 3).isTrue();
    }

    @Test
    @DisplayName("id 를 기준으로 root 페이지까지의 id 목록을 조회하고, 목록을 통해 페이지 목록을 조회한다.")
    void findAllById() {
        List<Long> ids = pageRepository.findParentPageIds(363L);
        ids.forEach(System.out::println);
        assertThat(ids.size() == 4).isTrue();

        List<PageInfo> pageInfos = pageRepository.findAllById(ids);
        assertThat(pageInfos.size() == 4).isTrue();
    }

    @Test
    @DisplayName("id 를 기준으로 root 페이지까지의 id를 순서대로 조회한다.")
    void findParentPageIdsTest() {
        // 363 번 페이지는 5 Level
        List<Long> ids = pageRepository.findParentPageIds(363L);
        assertThat(ids.size() == 4).isTrue();
    }
}