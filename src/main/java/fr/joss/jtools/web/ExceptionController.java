package fr.joss.jtools.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Classe captant toutes les exceptions sortant des contrôleurs
 *
 * @author jntakpe
 */
@ControllerAdvice
public class ExceptionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(Exception e) {
        logger.error(e.getMessage(), e);
        ModelAndView mv = new ModelAndView("exception");
        mv.addObject("exception", e);
        return mv;
    }
}
