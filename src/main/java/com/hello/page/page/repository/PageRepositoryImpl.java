package com.hello.page.page.repository;

import com.hello.page.page.entity.Page;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PageRepositoryImpl implements PageRepository {

    @Override
    public Optional<Page> findById(Long id){
        Page page = new Page();

        return Optional.ofNullable(page);
    }

    @Override
    public Page save(Page page){
        return page;
    }
}
