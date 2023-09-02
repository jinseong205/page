package com.hello.page.service;

import com.hello.page.TestData;
import com.hello.page.domain.PageInfo;
import com.hello.page.repository.PageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PageServiceImplTest {
    @Mock
    PageRepository pageRepository;
    PageServiceImpl pageService;

    @BeforeEach
    void beforeEach() {
        this.pageService = new PageServiceImpl(this.pageRepository);
    }

    @Test
    @DisplayName("존재하는 페이지를 조회하면 해당 페이지 정보를 담은 Optional<Page> 를 반환한다.")
    void readExistingPage() {
        // given
        var testData = new TestData();
        var ancestorPageIds = testData.ancestorPages.stream().map(PageInfo::getId).toList();
        when(this.pageRepository.findById(testData.pageInfo.getId()))
                .thenReturn(Optional.of(testData.pageInfo));
        when(this.pageRepository.findByParentId(testData.pageInfo.getId()))
                .thenReturn(testData.subPages);
        when(this.pageRepository.findParentPageIds(testData.pageInfo.getId()))
                .thenReturn(ancestorPageIds);
        when(this.pageRepository.findAllById(ancestorPageIds))
                .thenReturn(testData.ancestorPages);

        // when
        var result = this.pageService.readPage(testData.pageInfo.getId());

        // then
        assertThat(result.isPresent()).isTrue();
        var readPage = result.get();
        assertThat(readPage).isEqualTo(testData.page);
    }

    @Test
    @DisplayName("존재하지 않는 페이지를 조회하면 빈 Optional<Page> 를 반환한다.")
    void readNotExistingPage() {
        // given
        var pageId = 1L;
        when(this.pageRepository.findById(pageId))
                .thenReturn(Optional.empty());

        // when
        var result = this.pageService.readPage(pageId);

        // then
        assertThat(result.isEmpty()).isTrue();
    }
}