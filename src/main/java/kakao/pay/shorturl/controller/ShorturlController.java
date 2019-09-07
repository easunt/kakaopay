package kakao.pay.shorturl.controller;

import kakao.pay.shorturl.service.ShorturlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class ShorturlController {
    private final ShorturlService shorturlService;

    @GetMapping
    public String home() {
        return "index";
    }

    @PostMapping
    public ModelAndView createShortUrl(@RequestParam String url) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("result", shorturlService.createShorturl(url));
        return modelAndView;
    }

    @GetMapping("{shortUrl}")
    public RedirectView getOriginalUrl(@PathVariable String shortUrl) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(shorturlService.getOriginalUrl(shortUrl));
        return redirectView;
    }

}

