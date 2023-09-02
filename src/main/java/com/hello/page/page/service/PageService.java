package com.hello.page.page.service;

import com.hello.page.page.entity.Page;
import com.hello.page.page.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PageService {
    private final PageRepository pageRepository;
    private Long page_id = 0L;

    public Page getPageById(Long id){
        return pageRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("못찾음"));
    }

    public Page createPage(String title, String contents, boolean isRoot, Long parrentPageId){
        if(isRoot){
            return createRootPage(title, contents);
        }
        else{
            Page parrentPage = this.getPageById(parrentPageId);
            return this.createSubPage(parrentPage, title, contents);
        }
    }

    private void createDummyPageDatas(){
        String title = "페이지 제목";
        String contents = "페이지 내용";
        Page rootPage = this.createRootPage("root"+title, "root"+contents);

    }
    private Page createRootPage(String title, String contents){
        Page page = new Page();
        page.setContents(contents);
        page.setTitle(title);
        page.addBreadCrumbs("/"+title);
        return pageRepository.save(page);
    }
    private Page createSubPage(Page parrentPage, String title, String contents){
        Page page = new Page();
        page.setContents(contents);
        page.setTitle(title);
        for (String parrentBreadCrumb : parrentPage.getBreadCrumbs()){
            page.addBreadCrumbs(parrentBreadCrumb);
        }
        page.addBreadCrumbs("/"+title);
        page = pageRepository.save(page);
        parrentPage.addSubPageList(page.getId());
        pageRepository.save(parrentPage);
        return page;
    }

    public ArrayList<Page> getSubPage(ArrayList<Long> subPageIdList){
        ArrayList<Page> subPageList = new ArrayList<>();
        System.out.println("A");
        for (Long subPageId: subPageIdList) {
            Page subPage = this.getPageById(subPageId);
            subPageList.add(subPage);
        }
        System.out.println("B");
        return subPageList;
    }
}
