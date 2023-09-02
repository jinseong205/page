package com.hello.page.application.page.inboundport;

import com.hello.page.application.page.domain.ParentPage;

import java.util.Optional;

public interface PageService {
    Optional<ParentPage> readPage(Long pageId);
}
