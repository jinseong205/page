package com.hello.page;

import com.hello.page.domain.Breadcrumb;
import com.hello.page.domain.Page;
import com.hello.page.domain.PageInfo;

import java.util.List;

public class TestData {
    public final PageInfo grandParentPageInfo;
    public final PageInfo parentPageInfo;
    public final PageInfo pageInfo;
    public final PageInfo childPageInfo1;
    public final PageInfo childPageInfo2;
    public final List<PageInfo> ancestorPages;
    public final List<PageInfo> subPages;
    public final Breadcrumb grandParentBreadcrumb;
    public final Breadcrumb parentBreadcrumb;
    public final Page page;

    public TestData() {
        this.grandParentPageInfo = PageInfo.builder()
                .id(1L)
                .title("부모-부모 페이지")
                .content("이것은 부모-부모 페이지입니다.")
                .parentId(null)
                .build();
        this.parentPageInfo = PageInfo.builder()
                .id(2L)
                .title("부모 페이지")
                .content("이것은 부모 페이지입니다.")
                .parentId(this.grandParentPageInfo.getId())
                .build();
        this.pageInfo = PageInfo.builder()
                .id(3L)
                .title("테스트 페이지")
                .content("이것은 테스트 페이지입니다.")
                .parentId(this.parentPageInfo.getId())
                .build();
        this.childPageInfo1 = PageInfo.builder()
                .id(4L)
                .title("자식 페이지 1")
                .content("이것은 첫 번째 자식 페이지입니다.")
                .parentId(this.pageInfo.getId())
                .build();
        this.childPageInfo2 = PageInfo.builder()
                .id(5L)
                .title("자식 페이지 2")
                .content("이것은 두 번째 자식 페이지입니다.")
                .parentId(this.pageInfo.getId())
                .build();

        this.ancestorPages = List.of(this.parentPageInfo, this.grandParentPageInfo);
        this.subPages = List.of(this.childPageInfo1, this.childPageInfo2);

        this.grandParentBreadcrumb = Breadcrumb.builder()
                .pageId(this.grandParentPageInfo.getId())
                .title(this.grandParentPageInfo.getTitle())
                .build();
        this.parentBreadcrumb = Breadcrumb.builder()
                .pageId(this.parentPageInfo.getId())
                .title(this.parentPageInfo.getTitle())
                .build();
        var breadcrumbs = List.of(this.grandParentBreadcrumb, this.parentBreadcrumb);
        this.page = Page.builder()
                .pageInfo(this.pageInfo)
                .subPages(this.subPages)
                .breadcrumbs(breadcrumbs)
                .build();
    }
}
