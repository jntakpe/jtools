package fr.joss.jtools.service.impl;

import fr.joss.jtools.domain.Question;
import fr.joss.jtools.domain.Quiz;
import fr.joss.jtools.repository.QuizRepository;
import fr.joss.jtools.service.QuestionService;
import fr.joss.jtools.service.QuizService;
import fr.joss.jtools.util.ResponseQuestion;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Test des services associés à l'entité {@link Question}
 *
 * @author jntakpe
 */
@ContextConfiguration("classpath*:applicationContext-test.xml")
public class QuestionServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizRepository quizRepository;

    @Test
    public void saveTest() {
        Quiz javaBasics = quizService.findOne(quizRepository.findByTitleIgnoreCase("Java basics").getId());
        //New question
        Question testQuestion = new Question();
        testQuestion.setQuiz(javaBasics);
        testQuestion.setCorrectAnswer(1);
        testQuestion.setDuration(20);
        testQuestion.setExplanation("Parce que c'est comme ca");
        testQuestion.setFirstAnswer("Gavin King");
        testQuestion.setSecondAnswer("James Gosling");
        testQuestion.setLabel("Qui a créé le langage Java ?");
        testQuestion.setNumber(javaBasics.getTotalQuestion());
        Question managedQuestion = questionService.save(testQuestion);
        quizService.findOne(javaBasics.getId());
        assertNotNull(questionService.findOne(managedQuestion.getId()));
        //Update
        Question firstQuestion = questionService.findOne(1L);
        firstQuestion.setLabel("test question");
        firstQuestion.setCorrectAnswer(1);
        assertEquals("test question", questionService.findOne(1L).getLabel());
        assertEquals(new Integer(1), questionService.findOne(1L).getCorrectAnswer());
    }

    @Test
    public void deleteTest() {
        Question firstQuestion = questionService.findOne(1L);
        questionService.delete(firstQuestion);
        Integer i = 0;
        for (Question question : quizService.findOne(1L).getQuestions()) {
            assertEquals(i, question.getNumber());
            i++;
        }
    }

    @Test
    public void findSortedIdsTest() {
        List<Long> ids = questionService.findSortedIds(1L);
        for (int i = 1; i < ids.size(); i++) {
            assertTrue(questionService.findOne(ids.get(i - 1)).getNumber()
                    < questionService.findOne(ids.get(i)).getNumber());
        }
        ids = questionService.findSortedIds(2L);
        for (int i = 1; i < ids.size(); i++) {
            assertTrue(questionService.findOne(ids.get(i - 1)).getNumber()
                    < questionService.findOne(ids.get(i)).getNumber());
        }
        ids = questionService.findSortedIds(3L);
        for (int i = 1; i < ids.size(); i++) {
            assertTrue(questionService.findOne(ids.get(i - 1)).getNumber()
                    < questionService.findOne(ids.get(i)).getNumber());
        }
    }

    @Test
    public void validCurrentGetNextTest() {
        //Security context mocking
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("jOSS", "lolilol"));
        ResponseQuestion resp = questionService.validCurrentGetNext(1L, 1);
        assertEquals(false, resp.isGoodAnswer());
        assertEquals(new Integer(3), resp.getCorrectAnswer());
        assertEquals(questionService.findOne(2L), resp.getQuestion());
        resp = questionService.validCurrentGetNext(2L, 4);
        assertEquals(true, resp.isGoodAnswer());
        assertEquals(new Integer(4), resp.getCorrectAnswer());
        assertEquals(questionService.findOne(3L), resp.getQuestion());
    }

    @Test
    public void findLastQuestionTest() {
        Quiz cinemaBasics = quizService.findOne(quizRepository.findByTitleIgnoreCase("Cinema basics").getId());
        List<Question> questions = cinemaBasics.getQuestions();
        //Security context mocking
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("jOSS", "lolilol"));
        assertEquals(questions.get(1), questionService.findLastQuestion(cinemaBasics));
        //Security context mocking
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("jujupiwi", "lolilol"));
        assertEquals(questions.get(2), questionService.findLastQuestion(cinemaBasics));
    }
}
