package com.hello.page.application.page.outboundport;

import com.hello.page.application.page.domain.Page;

import java.util.Optional;

public interface PageRepository {
    Optional<Page> readPage(Long pageId);
}
