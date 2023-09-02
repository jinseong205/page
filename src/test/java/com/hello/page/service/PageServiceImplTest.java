package com.hello.page.service;

import com.hello.page.domain.Breadcrumb;
import com.hello.page.domain.Page;
import com.hello.page.domain.PageInfo;
import com.hello.page.repository.PageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
        var grandParentPageInfo = PageInfo.builder()
                .id(1L)
                .title("부모-부모 페이지")
                .content("이것은 부모-부모 페이지입니다.")
                .parentId(null)
                .build();
        var parentPageInfo = PageInfo.builder()
                .id(2L)
                .title("부모 페이지")
                .content("이것은 부모 페이지입니다.")
                .parentId(grandParentPageInfo.getId())
                .build();
        var pageInfo = PageInfo.builder()
                .id(3L)
                .title("테스트 페이지")
                .content("이것은 테스트 페이지입니다.")
                .parentId(parentPageInfo.getId())
                .build();
        var childPageInfo1 = PageInfo.builder()
                .id(4L)
                .title("자식 페이지 1")
                .content("이것은 첫 번째 자식 페이지입니다.")
                .parentId(pageInfo.getId())
                .build();
        var childPageInfo2 = PageInfo.builder()
                .id(5L)
                .title("자식 페이지 2")
                .content("이것은 두 번째 자식 페이지입니다.")
                .parentId(pageInfo.getId())
                .build();
        var ancestorPages = List.of(parentPageInfo, grandParentPageInfo);
        var subPages = List.of(childPageInfo1, childPageInfo2);
        var grandParentBreadcrumb = Breadcrumb.builder()
                .pageId(grandParentPageInfo.getId())
                .title(grandParentPageInfo.getTitle())
                .build();
        var parentBreadcrumb = Breadcrumb.builder()
                .pageId(parentPageInfo.getId())
                .title(parentPageInfo.getTitle())
                .build();
        var breadcrumbs = List.of(grandParentBreadcrumb, parentBreadcrumb);
        var page = Page.builder()
                .pageInfo(pageInfo)
                .subPages(subPages)
                .breadcrumbs(breadcrumbs)
                .build();
        var ancestorPageIds = ancestorPages.stream().map(PageInfo::getId).toList();
        when(this.pageRepository.findById(pageInfo.getId()))
                .thenReturn(Optional.of(pageInfo));
        when(this.pageRepository.findByParentId(pageInfo.getId()))
                .thenReturn(subPages);
        when(this.pageRepository.findParentPageIds(pageInfo.getId()))
                .thenReturn(ancestorPageIds);
        when(this.pageRepository.findAllById(ancestorPageIds))
                .thenReturn(ancestorPages);

        // when
        var result = this.pageService.readPage(pageInfo.getId());

        // then
        assertThat(result.isPresent()).isTrue();
        var readPage = result.get();
        assertThat(readPage).isEqualTo(page);
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