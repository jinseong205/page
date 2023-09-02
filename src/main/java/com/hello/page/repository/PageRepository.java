package com.hello.page.repository;

import com.hello.page.entity.Page;

import java.util.List;
import java.util.Optional;

public interface PageRepository {
    Optional<Page> findByPageId(Long pageId);

    List<Page> findByParentPageId(Long parentPageId);

    List<Page> findAllByPageId(Long pageId);
}
