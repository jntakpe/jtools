package fr.joss.jtools.service.impl;

import com.google.common.collect.Lists;
import fr.joss.jtools.domain.*;
import fr.joss.jtools.repository.QuizRepository;
import fr.joss.jtools.repository.QuizUserRepository;
import fr.joss.jtools.service.QuestionService;
import fr.joss.jtools.service.QuizService;
import fr.joss.jtools.service.UserService;
import fr.joss.jtools.util.ResponseQuestion;
import org.joda.time.Instant;
import org.joda.time.Minutes;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Test des services associés à l'entité {@link Quiz}
 *
 * @author jntakpe
 */
@ContextConfiguration("classpath*:applicationContext-test.xml")
public class QuizServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizUserRepository quizUserRepository;

    @Test
    public void isTitleAvailableTest() {
        Quiz javaBasics = quizService.findOne(1L);
        assertFalse(quizService.isTitleAvailable(null, "Java basics"));
        assertFalse(quizService.isTitleAvailable(null, "java BASICS"));
        assertFalse(quizService.isTitleAvailable(999L, "java BASICS"));
        assertTrue(quizService.isTitleAvailable(javaBasics.getId(), "Java basics"));
        assertTrue(quizService.isTitleAvailable(999L, "Stupid test title"));
        assertTrue(quizService.isTitleAvailable(null, "Stupid test title"));
    }

    @Test
    public void findByCreatorTest() {
        User jOSS = userService.findByLogin("jOSS");
        List<Quiz> jOSSQuiz = quizService.findByCreator(jOSS);
        Quiz javaBasics = quizRepository.findByTitleIgnoreCase("Java basics");
        assertTrue(jOSSQuiz.contains(javaBasics));
        Quiz footBasics = quizRepository.findByTitleIgnoreCase("Foot Basics");
        assertTrue(jOSSQuiz.contains(footBasics));
        User juju = userService.findByLogin("Jujupiwi");
        List<Quiz> jujuQuiz = quizService.findByCreator(juju);
        Quiz cinemaBasics = quizRepository.findByTitleIgnoreCase("Cinema basics");
        assertTrue(jujuQuiz.contains(cinemaBasics));
    }

    @Test
    public void findOneTest() {
        Quiz javaBasics = quizService.findOne(quizRepository.findByTitleIgnoreCase("Java basics").getId());
        assertNotNull(javaBasics.getTotalQuestion());
        assertTrue(javaBasics.getTotalQuestion() > 0);
    }

    @Test
    public void saveTest() {
        //Security context mocking
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("jOSS", "lolilol"));
        //New quiz
        Long nbQuiz = quizService.count();
        Quiz emptyQuiz = new Quiz();
        emptyQuiz.setTitle("Empty quiz");
        quizService.save(emptyQuiz);
        assertEquals(nbQuiz + 1, quizService.count());
        assertTrue(quizService.findByCreator(userService.findByLogin("jOSS")).contains(emptyQuiz));
        Quiz managedEmptyQuiz = quizRepository.findByTitleIgnoreCase("empty quiz");
        assertNotNull(managedEmptyQuiz);
        assertEquals("jOSS", managedEmptyQuiz.getCreator().getLogin());
        assertTrue(Minutes.minutesBetween(Instant.now(), new Instant(managedEmptyQuiz.getCreateDate()))
                .isLessThan(Minutes.ONE));
        //Update quiz
        managedEmptyQuiz.setTitle("Not empty anymore");
        assertNull(quizRepository.findByTitleIgnoreCase("empty quiz"));
        assertNotNull(quizRepository.findByTitleIgnoreCase("Not empty anymore"));
    }

    @Test
    public void saveResultsTest() {
        //Security context mocking
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("jOSS", "lolilol"));
        User joss = userService.findByLogin("joss");
        Quiz footBasics = quizRepository.findByTitleIgnoreCase("Foot Basics");
        int correct = 0;
        for (Question question : footBasics.getQuestions()) {
            ResponseQuestion resp = questionService.validCurrentGetNext(question.getId(), 2);
            if (resp.isGoodAnswer())
                correct++;
        }
        quizService.saveResult(footBasics.getId());
        QuizUserId quizUserId = new QuizUserId();
        quizUserId.setUser(joss);
        quizUserId.setQuiz(footBasics);
        QuizUser footBasicsJossResults = quizUserRepository.findOne(quizUserId);
        assertNotNull(footBasicsJossResults.getResult());
        Integer percentCorrect = correct * 100 / footBasics.getQuestions().size();
        assertNotEquals(new Integer(0), percentCorrect);
        assertEquals(percentCorrect, footBasicsJossResults.getResult());
    }

    @Test
    public void averageQuizResultTest() {
        assertEquals(new Integer(100), quizService.averageQuizResult(
                quizRepository.findByTitleIgnoreCase("Java basics")));
        assertEquals(new Integer(83), quizService.averageQuizResult(
                quizRepository.findByTitleIgnoreCase("Cinema basics")));
        assertEquals(new Integer(75), quizService.averageQuizResult(
                quizRepository.findByTitleIgnoreCase("Foot basics")));
    }

    @Test
    public void firstTimeQuizTest() {
        User joss = userService.findByLogin("jOSS");
        int allQuizSize = Lists.newArrayList(quizService.findAll()).size();
        assertEquals(allQuizSize - joss.getQuizUsers().size(), quizService.findUndoneQuiz(joss).size());
        User jujupiwi = userService.findByLogin("jujupiwi");
        assertEquals(allQuizSize - jujupiwi.getQuizUsers().size(), quizService.findUndoneQuiz(jujupiwi).size());
        User selrak = userService.findByLogin("selrak");
        assertEquals(allQuizSize - selrak.getQuizUsers().size(), quizService.findUndoneQuiz(selrak).size());
    }

    @Test
    public void hasDoneTest() {
        Quiz javaBasics = quizRepository.findByTitleIgnoreCase("Java basics");
        Quiz cinemaBasics = quizRepository.findByTitleIgnoreCase("Cinema basics");
        Quiz footBasics = quizRepository.findByTitleIgnoreCase("Foot basics");
        User joss = userService.findByLogin("jOSS");
        User juju = userService.findByLogin("jujupiwi");
        User selrak = userService.findByLogin("selrak");
        assertTrue(quizService.hasDone(joss, javaBasics.getId()));
        assertTrue(quizService.hasDone(joss, cinemaBasics.getId()));
        assertFalse(quizService.hasDone(joss, footBasics.getId()));
        assertFalse(quizService.hasDone(juju, javaBasics.getId()));
        assertTrue(quizService.hasDone(juju, cinemaBasics.getId()));
        assertTrue(quizService.hasDone(juju, footBasics.getId()));
        assertFalse(quizService.hasDone(selrak, javaBasics.getId()));
        assertFalse(quizService.hasDone(selrak, cinemaBasics.getId()));
        assertFalse(quizService.hasDone(selrak, footBasics.getId()));
    }

    @Test
    public void deleteTest() {
        long nbQuiz = quizService.count();
        Quiz javaBasics = quizRepository.findByTitleIgnoreCase("Java basics");
        assertNotNull(javaBasics);
        quizService.delete(javaBasics);
        assertEquals(nbQuiz - 1, quizService.count());
        assertNull(quizRepository.findByTitleIgnoreCase("Java basics"));
        assertNotNull(userService.findByLogin("jOSS"));
        QuizUserId quizUserId = new QuizUserId();
        quizUserId.setUser(userService.findByLogin("jOSS"));
        quizUserId.setQuiz(javaBasics);
        assertNull(quizUserRepository.findOne(quizUserId));
    }

}
