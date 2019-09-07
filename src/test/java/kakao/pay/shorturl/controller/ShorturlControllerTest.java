package kakao.pay.shorturl.controller;

import kakao.pay.shorturl.service.ShorturlService;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuipackage kakao.pay.shorturl.controller;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebMvcTest(ShorturlController.class)
public class ShorturlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShorturlService shorturlService;

    @Test
    public void 단축URL_생성_OK() throws Exception {
        //given
        given(shorturlService.createShorturl("https://www.naver.com")).willReturn("http://localhost/Y6666");
        //when
        final MockHttpServletResponse response = mockMvc.perform(post(""))
                .andDo(print())
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotBlank();
    }

    @Test
    public void 단축URL_가져오기_OK() throws Exception {
        //given
        given(shorturlService.getOriginalUrl("Y6666")).willReturn("https://www.naver.com");
    }
}
lders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebMvcTest(ShorturlController.class)
public class ShorturlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShorturlService shorturlService;

    @Test
    public void 단축URL_생성_OK() throws Exception {
        //given
        given(shorturlService.createShorturl("https://www.naver.com")).willReturn("http://localhost/Y6666");
        //when
        final MockHttpServletResponse response = mockMvc.perform(post(""))
                .andDo(print())
                .andReturn()
                .getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isNotBlank();
    }

    @Test
    public void 단축URL_가져오기_OK() throws Exception {
        //given
        given(shorturlService.getOriginalUrl("Y6666")).willReturn("https://www.naver.com");
    }
}
