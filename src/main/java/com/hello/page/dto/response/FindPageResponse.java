package com.hello.page.dto.response;

import com.hello.page.entity.Page;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FindPageResponse {
    private Long pageId;
    private String title;
    private String content;
    private List<Page> subPages;
    private List<String> breadcrumbs;
}
