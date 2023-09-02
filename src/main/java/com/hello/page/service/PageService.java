package com.hello.page.service;

import com.hello.page.domain.Page;

import java.util.Optional;

public interface PageService {
    Optional<Page> readPage(Long pageId);
}
