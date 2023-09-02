package com.hello.page.service;

import com.hello.page.domain.Breadcrumb;
import com.hello.page.domain.Page;
import com.hello.page.domain.PageInfo;
import com.hello.page.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PageServiceImpl implements PageService {
    private final PageRepository pageRepository;

    @Override
    public Optional<Page> readPage(Long pageId) {
        // @TODO 리포지토리에서 pageId 에 해당하는 Optional<PageInfo> 조회
        return Optional.<PageInfo>empty()
                .map(this::constructPageFromPageInfo);
    }

    /**
     * PageInfo 로부터 해당 페이지를 표현하는 Page 생성
     */
    private Page constructPageFromPageInfo(PageInfo pageInfo) {
        // @TODO 리포지토리에서 pageInfo.id 를 parentId 로 가지는 PageInfo 목록 조회
        var subPages = new ArrayList<PageInfo>();

        // breadcrumbs 구성
        var breadcrumbs = this.constructBreadcrumbsFromPageInfo(pageInfo);

        return Page.builder()
                .pageInfo(pageInfo)
                .subPages(subPages)
                .breadcrumbs(breadcrumbs)
                .build();
    }

    /**
     * PageInfo 로부터 해당 페이지의 모든 조상 페이지를
     * 가장 먼 조상 -> 가장 가까운 조상 순서로 표현하는 List<Breadcrumb> 생성
     */
    private List<Breadcrumb> constructBreadcrumbsFromPageInfo(PageInfo pageInfo) {
        // @TODO 구현
        // 1. 리포지토리에서 부모 페이지 아이디 목록 조회 (재귀 쿼리)
        // 2. 리포지토리에서 부모 페이지의 PageInfo 목록 조회 (WHERE id IN)
        // 3. 조회한 결과로 List<Breadcrumb> 구성
        return new ArrayList<>();
    }
}
