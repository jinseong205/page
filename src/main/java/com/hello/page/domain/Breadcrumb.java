package com.hello.page.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Breadcrumb {
    private final Long pageId;
    private final String title;
}
