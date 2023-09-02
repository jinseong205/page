package com.hello.page.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageInfo {
    @JsonSerialize(using = ToStringSerializer.class)
    private final Long id;
    private final String title;
    private final String content;
    @JsonSerialize(using = ToStringSerializer.class)
    private final Long parentId;
}
