package fr.joss.jtools.web;

import fr.joss.jtools.domain.Parameter;
import fr.joss.jtools.service.ParameterService;
import fr.joss.jtools.util.dto.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôlleur gérant l'écran de calcul sur l'entité {@link Parameter}
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/admin/param")
public class ParameterController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ParameterService parameterService;

    @RequestMapping(method = RequestMethod.GET)
    public String display() {
        return "parameter_list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Parameter> list() {
        return parameterService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute Parameter parameter) {
        boolean isNew = parameter.getId() == null;
        String msg;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        parameterService.save(parameter);
        if (isNew) {
            logger.info("{} a créé le paramètre {}", username, parameter);
            msg = "Paramètre '" + parameter.getKey() + "' créé.";
        } else {
            logger.info("{} a modifié le paramètre {}", username, parameter);
            msg = "Paramètre '" + parameter.getKey() + "' modifié.";
        }
        ModelAndView mv = new ModelAndView("parameter_list");
        return mv.addObject("responseMessage", ResponseMessage.getSuccessMessage(msg));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseMessage delete(@PathVariable Long id) {
        Parameter parameter = parameterService.findOne(id);
        parameterService.delete(parameter);
        return ResponseMessage.getSuccessMessage("Paramètre '" + parameter.getKey() + "' supprimé");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage loadParam(@PathVariable Long id) {
        return ResponseMessage.getSuccessMessage("", parameterService.findOne(id));
    }
}
