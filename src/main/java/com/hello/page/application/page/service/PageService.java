package com.hello.page.application.page.service;

import com.hello.page.application.page.domain.ParentPage;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PageService {
    public Optional<ParentPage> readPage(Long pageId) {
        throw new RuntimeException("not implemented");
    }
}
