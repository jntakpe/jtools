package fr.joss.jtools.repository;

import fr.joss.jtools.domain.Quiz;
import fr.joss.jtools.domain.QuizUser;
import fr.joss.jtools.domain.QuizUserId;
import fr.joss.jtools.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Interface de manipulation de l'entité {@link QuizUser}. Implémentation générée par Spring Data JPA.
 *
 * @author jntakpe
 * @see CrudRepository
 */
public interface QuizUserRepository extends CrudRepository<QuizUser, QuizUserId> {

    @Query("SELECT q.result FROM QuizUser q WHERE q.quizUserId.quiz = :quiz")
    List<Integer> findQuizResultByQuiz(@Param("quiz") Quiz quiz);

    @Query("SELECT q.quizUserId.quiz FROM QuizUser q WHERE q.quizUserId.user = :user")
    List<Quiz> findDoneQuizByUser(@Param("user") User user);

    @Query("SELECT q FROM QuizUser q WHERE q.quizUserId.user = :user")
    List<QuizUser> findQuizUserByUser(@Param("user") User user);

    @Query("SELECT q FROM QuizUser q WHERE q.quizUserId.quiz = :quiz ORDER BY q.result DESC")
    List<QuizUser> findQuizUserByQuiz(@Param("quiz") Quiz quiz);
}
