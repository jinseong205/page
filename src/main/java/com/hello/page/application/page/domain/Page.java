package com.hello.page.application.page.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Page {
    private Long id;
    private String title;
    private String content;
    private Long parentId;
}
