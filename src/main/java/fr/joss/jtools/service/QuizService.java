package fr.joss.jtools.service;

import fr.joss.jtools.domain.Quiz;
import fr.joss.jtools.domain.QuizUser;
import fr.joss.jtools.domain.User;

import java.util.List;

/**
 * Services à la gestion de l'entité {@link Quiz}
 *
 * @author jntakpe
 * @see GenericService
 */
public interface QuizService extends GenericService<Quiz> {

    boolean isTitleAvailable(Long id, String title);

    List<Quiz> findByCreator(User creator);

    QuizUser saveResult(Long quizId);

    Integer averageQuizResult(Quiz quiz);

    List<Quiz> firstTimeQuiz(User user);

    boolean hasDone(User user, Long quizId);
}
