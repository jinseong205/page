package com.hello.page.page.controller;

import com.hello.page.page.dto.CreatePageRequest;
import com.hello.page.page.dto.CreatePageResponse;
import com.hello.page.page.entity.Page;
import com.hello.page.page.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pages")
@RequiredArgsConstructor
public class PageController {
    private final PageService pageService;


    @PostMapping("")
    public ResponseEntity<Object> createPage(@RequestBody CreatePageRequest createPageRequest){
        Page page = pageService.createPage(
                createPageRequest.getTitle(),
                createPageRequest.getContents(),
                createPageRequest.isRoot(),
                createPageRequest.getParrentPageId());
        return ResponseEntity.ok(new CreatePageResponse(page, pageService.getSubPage(page.getSubPageList())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readPage(@PathVariable Long id){
        Page page = pageService.getPageById(id);
        return ResponseEntity.ok(new CreatePageResponse(page, pageService.getSubPage(page.getSubPageList())));
    }


}
