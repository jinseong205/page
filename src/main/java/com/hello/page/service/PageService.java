package com.hello.page.service;

import com.hello.page.dto.response.FindPageResponse;
import com.hello.page.entity.Page;
import com.hello.page.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PageService {
    private final PageRepository pageRepository;

    public FindPageResponse findPage(Long pageId) {
        // 서브 페이지 조회
        List<Page> subPages = pageRepository.findByParentPageId(pageId);
        // 루트까지 모든 페이지 조회
        List<Page> pageList = pageRepository.findAllByPageId(pageId);

        // 현재 페이지
        Page now = pageList.get(0);
        // 브로드 크럼스 만들기
        Collections.reverse(pageList);
        List<String> breadcrumbs = pageList.stream()
                .filter(page -> !page.getPageId().equals(now.getPageId()))
                .map(Page::getTitle)
                .toList();

        return FindPageResponse.builder()
                .pageId(now.getPageId())
                .title(now.getTitle())
                .content(now.getContent())
                .subPages(subPages)
                .breadcrumbs(breadcrumbs)
                .build();
    }
}
