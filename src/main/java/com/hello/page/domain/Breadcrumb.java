package com.hello.page.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Breadcrumb {
    @JsonSerialize(using = ToStringSerializer.class)
    private final Long pageId;
    private final String title;
}
