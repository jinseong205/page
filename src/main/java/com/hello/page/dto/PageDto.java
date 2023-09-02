package com.hello.page.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageDto  {

    private Long id;
    private String title;
    private String content;
    private Long parent_id;
    private List<PageDto> subpages;
    private List<String> breadcrumbs;
    
}
