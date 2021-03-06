package fr.joss.jtools.service;

import fr.joss.jtools.domain.Quiz;
import fr.joss.jtools.domain.QuizUser;
import fr.joss.jtools.domain.User;
import fr.joss.jtools.util.dto.QuizStats;
import fr.joss.jtools.util.dto.UserStats;

import java.util.Collection;
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

    Collection<Quiz> findUndoneQuiz(User user);

    boolean hasDone(User user, Long quizId);

    List<UserStats> calcUserStats();

    List<QuizStats> calcQuizStats();
}
