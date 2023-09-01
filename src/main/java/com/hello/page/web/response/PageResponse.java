package com.hello.page.web.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponse {
    private String pageId;
    private String title;
    private String content;
    private List<PageResponse> subPages;
    private List<String> breadcrumbs;
}
