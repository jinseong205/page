package com.hello.page.controller;

import com.hello.page.TestData;
import com.hello.page.service.PageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
        var testDate = new TestData();
        Mockito.when(this.pageService.readPage(testDate.pageInfo.getId()))
                .thenReturn(Optional.of(testDate.page));

        // when & then
        this.mockMvc
                .perform(get("/pages/" + testDate.pageInfo.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testDate.pageInfo.getId().toString()))
                .andExpect(jsonPath("$.title").value(testDate.pageInfo.getTitle()))
                .andExpect(jsonPath("$.content").value(testDate.pageInfo.getContent()))
                .andExpect(jsonPath("$.parentId").value(testDate.pageInfo.getParentId().toString()))
                .andExpect(jsonPath("$.breadcrumbs").isArray())
                .andExpect(jsonPath("$.breadcrumbs", hasSize(2)))
                .andExpect(jsonPath("$.breadcrumbs[0].pageId")
                        .value(testDate.grandParentBreadcrumb.getPageId().toString()))
                .andExpect(jsonPath("$.breadcrumbs[0].title")
                        .value(testDate.grandParentBreadcrumb.getTitle()))
                .andExpect(jsonPath("$.breadcrumbs[1].pageId")
                        .value(testDate.parentBreadcrumb.getPageId().toString()))
                .andExpect(jsonPath("$.breadcrumbs[1].title")
                        .value(testDate.parentBreadcrumb.getTitle()))
                .andExpect(jsonPath("$.subPages").isArray())
                .andExpect(jsonPath("$.subPages", hasSize(2)))
                .andExpect(jsonPath("$.subPages[0].id")
                        .value(testDate.childPageInfo1.getId().toString()))
                .andExpect(jsonPath("$.subPages[0].title")
                        .value(testDate.childPageInfo1.getTitle()))
                .andExpect(jsonPath("$.subPages[0].content")
                        .value(testDate.childPageInfo1.getContent()))
                .andExpect(jsonPath("$.subPages[0].parentId")
                        .value(testDate.childPageInfo1.getParentId().toString()))
                .andExpect(jsonPath("$.subPages[1].id")
                        .value(testDate.childPageInfo2.getId().toString()))
                .andExpect(jsonPath("$.subPages[1].title")
                        .value(testDate.childPageInfo2.getTitle()))
                .andExpect(jsonPath("$.subPages[1].content")
                        .value(testDate.childPageInfo2.getContent()))
                .andExpect(jsonPath("$.subPages[1].parentId")
                        .value(testDate.childPageInfo2.getParentId().toString()));
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