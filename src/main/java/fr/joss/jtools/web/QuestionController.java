package fr.joss.jtools.web;

import fr.joss.jtools.domain.Question;
import fr.joss.jtools.service.QuestionService;
import fr.joss.jtools.util.IdVersion;
import fr.joss.jtools.util.ResponseMessage;
import fr.joss.jtools.util.ResponseQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return ResponseMessage.getSuccessMessage("Question " + question + " créée.", maj);
        } else {
            logger.info("{} a modifié la question '{}'", username, question);
            return ResponseMessage.getSuccessMessage("Question " + question + " modifiée.", maj);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Question find(@PathVariable Long id) {
        return questionService.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseMessage delete(@PathVariable Long id) {
        Question question = questionService.findOne(id);
        questionService.delete(question);
        return ResponseMessage.getSuccessMessage("Question " + question + " supprimée.");
    }

    @RequestMapping(value = "/{id}/list", method = RequestMethod.GET)
    @ResponseBody
    public List<Long> list(@PathVariable Long id) {
        return questionService.findSortedIds(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseQuestion nextQuestion(@PathVariable Long id, @RequestParam Integer answer) {
        ResponseQuestion responseQuestion = questionService.validCurrentGetNext(id, answer);
        Question question = responseQuestion.getQuestion();
        if (question != null) {
            question.setExplanation("Don't cheat");
            question.setCorrectAnswer(51);
            responseQuestion.setQuestion(question);
        }
        return responseQuestion;
    }
}
