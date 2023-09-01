package com.hello.page.application.page.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Breadcrumb {
    private Long pageId;
    private String title;
}
