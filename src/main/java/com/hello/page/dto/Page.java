package com.hello.page.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Page  {

    private Long id;
    private String title;
    private String content;
    private Long parent_id;
    private List<Page> subpages;
    private List<String> breadcrumbs;
    
}
