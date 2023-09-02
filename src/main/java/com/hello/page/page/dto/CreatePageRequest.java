package com.hello.page.page.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePageRequest {

    private String title;
    private String contents;
    private boolean isRoot;
    private Long parrentPageId;
}
