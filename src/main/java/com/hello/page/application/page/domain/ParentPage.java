package com.hello.page.application.page.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ParentPage {
    private final Page page;
    private final List<Page> subPages;
    private final List<Breadcrumb> breadcrumbs;
}
