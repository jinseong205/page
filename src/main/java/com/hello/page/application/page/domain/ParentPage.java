package com.hello.page.application.page.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class ParentPage {
    private Page page;
    private List<Page> subPages;
    private List<Breadcrumb> breadcrumbs;
}
