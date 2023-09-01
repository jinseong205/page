package com.hello.page.application.page.service;

import com.hello.page.application.page.domain.ParentPage;
import com.hello.page.application.page.outboundport.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PageService {
    private final PageRepository pageRepository;

    public Optional<ParentPage> readPage(Long pageId) {
        return this.pageRepository
                .findById(pageId)
                .map(page -> ParentPage.builder().page(page).build());
    }
}
