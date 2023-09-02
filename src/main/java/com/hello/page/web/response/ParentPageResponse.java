package com.hello.page.web.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ParentPageResponse {
    @JsonUnwrapped
    private PageResponse page;
    private List<PageResponse> subPages;
    private List<BreadcrumbResponse> breadcrumbs;
}
