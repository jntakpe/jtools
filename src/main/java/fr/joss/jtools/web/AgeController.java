package fr.joss.jtools.web;

import fr.joss.jtools.domain.Age;
import fr.joss.jtools.service.AgeService;
import fr.joss.jtools.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Contrôlleur gérant l'écran de calcul sur l'entité {@link Age}
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/age")
public class AgeController {

    @Autowired
    private UserService userService;

    @Autowired
    private AgeService ageService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView display() {
        ModelAndView mv = new ModelAndView("age_form");
        return mv.addObject(userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    @RequestMapping(value = "/calc", method = RequestMethod.GET)
    @ResponseBody
    public Age calc(@RequestParam Date birthdate) {
        return ageService.calcAge(birthdate);
    }

    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
    }
}
