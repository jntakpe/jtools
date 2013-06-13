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
    @ResponseBody
    public ResponseMessage save(@ModelAttribute Parameter parameter) {
        boolean isNew = parameter.getId() == null;
        String msg;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Parameter param = parameterService.save(parameter);
        if (isNew) {
            logger.info("{} a créé le paramètre {}", username, parameter);
            msg = "Paramètre '" + parameter.getKey() + "' créé.";
        } else {
            logger.info("{} a modifié le paramètre {}", username, parameter);
            msg = "Paramètre '" + parameter.getKey() + "' modifié.";
        }
        return ResponseMessage.getSuccessMessage(msg, param);
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
    public Parameter load(@PathVariable Long id) {
        return parameterService.findOne(id);
    }

    @RequestMapping(value = "/keyunicity", method = RequestMethod.GET)
    @ResponseBody
    public boolean controlUnicity(@RequestParam(required = false) Long id, @RequestParam String key) {
        return parameterService.isKeyAvailable(id , key);
    }
}
