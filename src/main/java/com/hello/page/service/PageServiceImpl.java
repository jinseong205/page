package com.hello.page.service;

import com.hello.page.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PageServiceImpl implements PageService {
    @Override
    public Optional<Page> readPage(Long pageId) {
        return Optional.empty();
    }
}
