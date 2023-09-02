package com.hello.page.page.dto;

import com.hello.page.page.entity.Page;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class CreatePageResponse {
    private Long id;
    private String title;
    private String contents;
    private ArrayList<Page> breadCrumbs;
    private ArrayList<Page> subPageList;

    public CreatePageResponse(Page page){
        this.id = page.getId();
        this.title = page.getTitle();
        this.contents = page.getContents();
        this.breadCrumbs = page.getBreadCrumbs();
        this.subPageList = page.getSubPageList();
    }

}
