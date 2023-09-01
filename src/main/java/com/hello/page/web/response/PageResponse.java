package com.hello.page.web.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponse {
    private String pageId;
    private String title;
    private String content;
}
