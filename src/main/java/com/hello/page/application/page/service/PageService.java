package com.hello.page.application.page.service;

import com.hello.page.application.page.domain.Breadcrumb;
import com.hello.page.application.page.domain.Page;
import com.hello.page.application.page.domain.ParentPage;
import com.hello.page.application.page.outboundport.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class PageService {
    private final PageRepository pageRepository;

    public Optional<ParentPage> readPage(Long pageId) {
        return this.pageRepository
                .findById(pageId)
                .map(page -> {
                    var subPages = this.pageRepository.findByParentId(page.getId());

                    var parentPageIds = this.pageRepository.findParentPageIds(page.getId());
                    var parentPageMap = this.pageRepository
                            .findAllById(parentPageIds)
                            .stream()
                            .collect(Collectors.toMap(Page::getId, Function.identity()));
                    var breadcrumbs = IntStream.range(0, parentPageIds.size())
                            .map(i -> parentPageIds.size() - 1 - i)
                            .mapToObj(parentPageIds::get)
                            .map(parentPageMap::get)
                            .peek(parentPage -> {
                                if (parentPage == null) {
                                    throw new IllegalStateException("부모 페이지 중 찾을 수 없는 것이 있습니다.");
                                }
                            })
                            .map(parentPage -> Breadcrumb.builder()
                                    .pageId(parentPage.getId())
                                    .title(parentPage.getTitle())
                                    .build())
                            .toList();

                    return ParentPage.builder()
                            .page(page)
                            .subPages(subPages)
                            .breadcrumbs(breadcrumbs)
                            .build();
                });
    }
}
