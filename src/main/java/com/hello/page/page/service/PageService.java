package com.hello.page.page.service;

import com.hello.page.page.entity.Page;
import com.hello.page.page.repository.PageRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageService {
    private final PageRepositoryImpl pageRepository;
    private Long page_id = 0L;


    public Page createPage(String title, String contents, boolean isRoot){
        if(isRoot){
            return createRootPage(title, contents);
        }
        return new Page();
    }
    private Page createRootPage(String title, String contents){
        Page page = new Page();
        page.setId(++this.page_id);
        page.setContents(contents);
        page.setTitle(title);
        return page;
        //return pageRepository.save(page);
    }
    private Page createSubPage(Page parrentPage, String title, String contents){
        Page page = new Page();
        page.setId(++this.page_id);
        page.setContents(contents);
        page.setTitle(title);
        parrentPage.addSubPage(page);
        return page;
        //return pageRepository.save(page);
    }

    public Page addSubPage(Page parrentPage, Page subPage){
        return parrentPage;
    }

    public Page getPage(Long pageId){
        return new Page();
    }
}
