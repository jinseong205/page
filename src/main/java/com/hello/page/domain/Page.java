package com.hello.page.domain;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Page {
    @JsonUnwrapped
    private final PageInfo pageInfo;
    private final List<PageInfo> subPages;
    private final List<Breadcrumb> breadcrumbs;
}
