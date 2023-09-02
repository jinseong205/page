package com.hello.page.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page {
    private Long pageId;
    private String title;
    private String content;
    private Long parentPageId;
}
