package com.hello.page.page.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter
@Setter
@Entity
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String contents;
    private Long parrentPageId;
    private ArrayList<Long> subPageList;
    private ArrayList<String> breadCrumbs;

    public Page(){
        this.subPageList = new ArrayList<Long>();
        this.breadCrumbs = new ArrayList<String>();
    }


    public void addSubPageList(Long subPageId){
        this.subPageList.add(subPageId);
    }

    public void addBreadCrumbs(String path){
        this.breadCrumbs.add(path);
    }

}
