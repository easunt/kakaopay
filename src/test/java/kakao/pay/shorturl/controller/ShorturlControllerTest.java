package kakao.pay.shorturl.controller;

import kakao.pay.shorturl.service.ShorturlService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ShorturlControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ShorturlService shorturlService;

    @InjectMocks
    private ShorturlController shorturlController;

    private final String ORIGINAL_URL = "https://www.kakaopay.com/";
    private final String SHORT_URL = "http://localhost/Y6666";
    private final String ENCOING_VALUE = "Y6666";


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders //4
                .standaloneSetup(shorturlController)
                .build();
    }

    @Test
    public void 기본페이지_OK() throws Exception {
        mockMvc.perform(get(""))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void 단축URL_생성_OK() throws Exception {

        when(shorturlService.createShorturl(any(java.lang.String.class))).thenReturn(SHORT_URL);

        mockMvc.perform(post("")
                .contentType(MediaType.APPLICATION_JSON)
                .param("url", ORIGINAL_URL))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("result"))
                .andExpect(model().attribute("result", SHORT_URL));
    }

    @Test
    public void 단축URL_리다이렉션_OK() throws Exception {

        when(shorturlService.getOriginalUrl(any(java.lang.String.class))).thenReturn(ORIGINAL_URL);

        mockMvc.perform(get("/" + ENCOING_VALUE))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ORIGINAL_URL));
    }

}