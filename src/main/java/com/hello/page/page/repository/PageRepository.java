package com.hello.page.page.repository;

import com.hello.page.page.entity.Page;


import java.util.Optional;

public interface PageRepository{
    public Optional<Page> findById(Long id);
    public Page save(Page page);
}
