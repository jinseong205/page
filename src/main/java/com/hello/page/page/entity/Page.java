package com.hello.page.page.entity;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter
@Setter
public class Page {
    private Long id;
    private String title;
    private String contents;
    private Page parrentPage;
    private ArrayList<Page> breadCrumbs;
    private ArrayList<Page> subPageList;

    public void addSubPage(Page subPage){
        this.subPageList.add(subPage);
    }

}
