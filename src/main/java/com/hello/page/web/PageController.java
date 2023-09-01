package com.hello.page.web;

import com.hello.page.web.response.PageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pages")
public class PageController {
    @GetMapping("/{pageId}")
    public PageResponse readPage(@PathVariable Long pageId) {
        throw new RuntimeException("not implemented");
    }
}
