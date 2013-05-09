package fr.joss.jtools.web;

import fr.joss.jtools.domain.Question;
import fr.joss.jtools.service.QuestionService;
import fr.joss.jtools.util.IdVersion;
import fr.joss.jtools.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur gérant les écrans relatifs à l'entité {@link Question}
 *
 * @author jntakpe
 */
@Controller
@RequestMapping("/question")
public class QuestionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionService questionService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage save(@ModelAttribute Question question, @RequestParam Long quizId) {
        boolean isNew = question.getId() == null;
        questionService.save(question, quizId);
        IdVersion maj = new IdVersion(question.getId(), question.getVersion());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (isNew) {
            logger.info("{} a créé la question '{}'", username, question);
            return ResponseMessage.getSuccessMessage("Question '" + question + "' créée.", maj);
        } else {
            logger.info("{} a modifié la question '{}'", username, question);
            return ResponseMessage.getSuccessMessage("Question '" + question + "' modifiée.", maj);
        }
    }
}
