package com.hello.page.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Page {
    private final PageInfo pageInfo;
    private final List<PageInfo> subPages;
    private final List<Breadcrumb> breadcrumbs;
}
