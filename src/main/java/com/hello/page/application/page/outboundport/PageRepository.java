package com.hello.page.application.page.outboundport;

import com.hello.page.application.page.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PageRepository {
    Optional<Page> findById(Long id);

    List<Page> findByParentId(Long parentId);

    /**
     * 가장 가까운 부모부터 가장 먼 부모 순서대로 정렬
     */
    List<Long> findParentPageIds(Long id);
}
