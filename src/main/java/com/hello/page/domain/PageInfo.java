package com.hello.page.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageInfo {
    private final Long id;
    private final String title;
    private final String content;
    private final Long parentId;
}
