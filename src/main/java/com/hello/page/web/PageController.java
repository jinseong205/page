package com.hello.page.web;

import com.hello.page.application.page.inboundport.PageService;
import com.hello.page.web.response.BreadcrumbResponse;
import com.hello.page.web.response.PageResponse;
import com.hello.page.web.response.ParentPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pages")
public class PageController {
    private final PageService pageService;

    @GetMapping("/{pageId}")
    public ParentPageResponse readPage(@PathVariable Long pageId) {
        return this.pageService
                .readPage(pageId)
                .map(parentPage -> {
                    var page = parentPage.getPage();
                    var pageRes = new PageResponse();
                    pageRes.setPageId(page.getId().toString());
                    pageRes.setTitle(page.getTitle());
                    pageRes.setContent(page.getContent());

                    var subPageResList = parentPage.getSubPages()
                            .stream()
                            .map(subPage -> {
                                var subPageRes = new PageResponse();
                                subPageRes.setPageId(subPage.getId().toString());
                                subPageRes.setTitle(subPage.getTitle());
                                subPageRes.setContent(subPage.getContent());
                                return subPageRes;
                            })
                            .toList();

                    var breadcrumbResList = parentPage.getBreadcrumbs()
                            .stream()
                            .map(breadcrumb -> {
                                var breadcrumbRes = new BreadcrumbResponse();
                                breadcrumbRes.setPageId(breadcrumb.getPageId().toString());
                                breadcrumbRes.setTitle(breadcrumb.getTitle());
                                return breadcrumbRes;
                            })
                            .toList();

                    var parentPageRes = new ParentPageResponse();
                    parentPageRes.setPage(pageRes);
                    parentPageRes.setSubPages(subPageResList);
                    parentPageRes.setBreadcrumbs(breadcrumbResList);

                    return parentPageRes;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
