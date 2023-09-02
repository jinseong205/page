package com.hello.page.controller;

import com.hello.page.dto.response.FindPageResponse;
import com.hello.page.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/page")
public class PageController {
    private final PageService pageService;

    @GetMapping("/{pageId}")
    public ResponseEntity<Object> findPage(@PathVariable("pageId") Long pageId) {
        FindPageResponse findPageResponse = pageService.findPage(pageId);
        return ResponseEntity.status(HttpStatus.OK).body(findPageResponse);
    }
}
