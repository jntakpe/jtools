package fr.joss.jtools.web;

import fr.joss.jtools.domain.Quiz;
import fr.joss.jtools.service.QuizService;
import fr.joss.jtools.service.UserService;
import fr.joss.jtools.util.IdVersion;
import fr.joss.jtools.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôleur gérant les écrans relatifs à l'entité {@link Quiz}
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/quiz")
public class QuizController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView form() {
        ModelAndView mv = new ModelAndView("quiz_form");
        return mv.addObject(new Quiz());
    }

    @RequestMapping(value = "/titlecontrol", method = RequestMethod.GET)
    @ResponseBody
    public boolean titleControl(@RequestParam Long id, @RequestParam String title) {
        return quizService.isTitleAvailable(id, title);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage save(@ModelAttribute Quiz quiz) {
        boolean isNew = quiz.getId() == null;
        quizService.save(quiz);
        IdVersion maj = new IdVersion(quiz.getId(), quiz.getVersion());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (isNew) {
            logger.info("{} a créé le quiz '{}'", username, quiz);
            return ResponseMessage.getSuccessMessage("Quiz '" + quiz + "' créé.", maj);
        } else {
            logger.info("{} a modifié le quiz '{}'", username, quiz);
            return ResponseMessage.getSuccessMessage("Quiz '" + quiz + "' modifié.", maj);
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String list() {
        return "quiz_edit_list";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("quiz_form");
        return mv.addObject(quizService.findOne(id));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Quiz> list(SecurityContextHolderAwareRequestWrapper request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return quizService.findAll();
        } else {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return quizService.findByCreator(userService.findByLogin(username));
        }
    }
}
