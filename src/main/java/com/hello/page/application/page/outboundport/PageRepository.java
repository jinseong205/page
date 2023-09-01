package com.hello.page.application.page.outboundport;

import com.hello.page.application.page.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PageRepository {
    Optional<Page> findById(Long id);

    List<Page> findByParentId(Long parentId);
}
