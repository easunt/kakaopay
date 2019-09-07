package kakao.pay.shorturl.common.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice(annotations = Controller.class)
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handler(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        RestException restException = new RestException(RestExceptionType.INTERNAL_SERVER_ERROR);

        restException.setMessage(exception.getMessage());
        modelAndView.setViewName("exception");
        modelAndView.addObject("exception", restException);

        return modelAndView;
    }

    @ExceptionHandler(RestException.class)
    public ModelAndView handler(RestException restException) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("exception");
        modelAndView.addObject("exception", restException);

        return modelAndView;
    }
}
