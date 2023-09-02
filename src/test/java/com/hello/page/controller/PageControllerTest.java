package com.hello.page.controller;

import com.hello.page.domain.Breadcrumb;
import com.hello.page.domain.Page;
import com.hello.page.domain.PageInfo;
import com.hello.page.service.PageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PageControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PageService pageService;

    @Test
    @DisplayName("존재하는 페이지를 조회하면 해당 페이지 정보를 응답한다.")
    void readExistingPage() throws Exception {
        // given
        var grandParentBreadcrumb = Breadcrumb.builder()
                .pageId(1L)
                .title("부모-부모 페이지")
                .build();
        var parentBreadcrumb = Breadcrumb.builder()
                .pageId(2L)
                .title("부모 페이지")
                .build();
        var pageInfo = PageInfo.builder()
                .id(3L)
                .title("테스트 페이지")
                .content("이것은 테스트 페이지입니다.")
                .parentId(parentBreadcrumb.getPageId())
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
                .content("이것을 두 번째 자식 페이지입니다.")
                .parentId(pageInfo.getId())
                .build();
        var page = Page.builder()
                .pageInfo(pageInfo)
                .subPages(List.of(childPageInfo1, childPageInfo2))
                .breadcrumbs(List.of(grandParentBreadcrumb, parentBreadcrumb))
                .build();
        Mockito.when(this.pageService.readPage(pageInfo.getId()))
                .thenReturn(Optional.of(page));

        // when & then
        this.mockMvc
                .perform(get("/pages/" + pageInfo.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(pageInfo.getId().toString()))
                .andExpect(jsonPath("$.title").value(pageInfo.getTitle()))
                .andExpect(jsonPath("$.content").value(pageInfo.getContent()))
                .andExpect(jsonPath("$.parentId").value(pageInfo.getParentId().toString()))
                .andExpect(jsonPath("$.breadcrumbs").isArray())
                .andExpect(jsonPath("$.breadcrumbs", hasSize(2)))
                .andExpect(jsonPath("$.breadcrumbs[0].pageId")
                        .value(grandParentBreadcrumb.getPageId().toString()))
                .andExpect(jsonPath("$.breadcrumbs[0].title")
                        .value(grandParentBreadcrumb.getTitle()))
                .andExpect(jsonPath("$.breadcrumbs[1].pageId")
                        .value(parentBreadcrumb.getPageId().toString()))
                .andExpect(jsonPath("$.breadcrumbs[1].title")
                        .value(parentBreadcrumb.getTitle()))
                .andExpect(jsonPath("$.subPages").isArray())
                .andExpect(jsonPath("$.subPages", hasSize(2)))
                .andExpect(jsonPath("$.subPages[0].id").value(childPageInfo1.getId().toString()))
                .andExpect(jsonPath("$.subPages[0].title").value(childPageInfo1.getTitle()))
                .andExpect(jsonPath("$.subPages[0].content").value(childPageInfo1.getContent()))
                .andExpect(jsonPath("$.subPages[0].parentId").value(childPageInfo1.getParentId().toString()))
                .andExpect(jsonPath("$.subPages[1].id").value(childPageInfo2.getId().toString()))
                .andExpect(jsonPath("$.subPages[1].title").value(childPageInfo2.getTitle()))
                .andExpect(jsonPath("$.subPages[1].content").value(childPageInfo2.getContent()))
                .andExpect(jsonPath("$.subPages[1].parentId").value(childPageInfo2.getParentId().toString()));
    }

    @Test
    @DisplayName("존재하지 않는 페이지를 조회하면 404 에러를 응답한다.")
    void readNotExistingPage() throws Exception {
        // given
        Mockito.when(this.pageService.readPage(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        // when & then
        this.mockMvc
                .perform(get("/pages/1"))
                .andExpect(status().isNotFound());
    }
}