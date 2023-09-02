package com.hello.page.controller;

import com.hello.page.domain.Page;
import com.hello.page.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pages")
public class PageController {
    private final PageService pageService;

    @GetMapping("/{pageId}")
    public Page readPage(@PathVariable Long pageId) {
        return this.pageService.readPage(pageId)
                .orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND));
    }
}
