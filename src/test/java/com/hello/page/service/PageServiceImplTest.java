package com.hello.page.service;

import com.hello.page.repository.PageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PageServiceImplTest {
    @Mock
    PageRepository pageRepository;
    PageServiceImpl pageService;

    @BeforeEach
    void beforeEach() {
        this.pageService = new PageServiceImpl(this.pageRepository);
    }

    @Test
    @DisplayName("존재하는 페이지를 조회하면 해당 페이지 정보를 담은 Optional<Page> 를 반환한다.")
    void readExistingPage() {
        // @TODO readPage 완성 이후 테스트 작성
        // mock 데이터는 controller 테스트에서 사용했던 데이터 재활용
    }

    @Test
    @DisplayName("존재하지 않는 페이지를 조회하면 빈 Optional<Page> 를 반환한다.")
    void readNotExistingPage() {
        // given
        var pageId = 1L;
        when(this.pageRepository.findById(pageId))
                .thenReturn(Optional.empty());

        // when
        var result = this.pageService.readPage(pageId);

        // then
        assertThat(result.isEmpty()).isTrue();
    }
}