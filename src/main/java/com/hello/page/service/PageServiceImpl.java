package com.hello.page.service;

import com.hello.page.domain.Breadcrumb;
import com.hello.page.domain.Page;
import com.hello.page.domain.PageInfo;
import com.hello.page.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PageServiceImpl implements PageService {
    private final PageRepository pageRepository;

    @Override
    public Optional<Page> readPage(Long pageId) {
        return this.pageRepository.findById(pageId)
                .map(this::constructPageFromPageInfo);
    }

    /**
     * PageInfo 로부터 해당 페이지를 표현하는 Page 생성
     */
    private Page constructPageFromPageInfo(PageInfo pageInfo) {
        var subPages = this.pageRepository.findByParentId(pageInfo.getId());

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
        // 부모 페이지 아이디 목록 조회
        var ids = pageRepository.findParentPageIds(pageInfo.getId());
        // 부모 페이지 목록 구성
        var pageInfos = new ArrayList<>(pageRepository.findAllById(ids));
        // 페이지 목록을 "가까운 부모 -> 먼 부모"를 역순 변환
        Collections.reverse(pageInfos);

        return pageInfos.stream()
                .map(page -> Breadcrumb.builder()
                        .pageId(page.getId())
                        .title(page.getTitle())
                        .build())
                .toList();
    }
}
