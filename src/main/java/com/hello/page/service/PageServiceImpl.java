package com.hello.page.service;

import com.hello.page.domain.Breadcrumb;
import com.hello.page.domain.Page;
import com.hello.page.domain.PageInfo;
import com.hello.page.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        var parentPageIds = this.pageRepository.findParentPageIds(pageInfo.getId());
        var parentPageInfoById = this.pageRepository
                .findAllById(parentPageIds)
                .stream()
                .collect(Collectors.toMap(PageInfo::getId, Function.identity()));
        var parentPageCount = parentPageIds.size();
        return IntStream.range(0, parentPageCount)
                .map(i -> parentPageCount - 1 - i)
                .mapToObj(parentPageIds::get)
                .map(parentPageInfoById::get)
                .peek(parentPageInfo -> {
                    if (parentPageInfo == null) {
                        throw new IllegalStateException("부모 페이지 중 찾을 수 없는 것이 있습니다.");
                    }
                })
                .map(parentPageInfo -> Breadcrumb.builder()
                        .pageId(parentPageInfo.getId())
                        .title(parentPageInfo.getTitle())
                        .build())
                .toList();
    }
}
