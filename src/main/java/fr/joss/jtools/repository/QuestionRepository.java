package fr.joss.jtools.repository;

import fr.joss.jtools.domain.Question;
import fr.joss.jtools.domain.Quiz;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Interface de manipulation de l'entité {@link Question}. Implémentation générée par Spring Data JPA.
 *
 * @author jntakpe
 * @see CrudRepository
 */
public interface QuestionRepository extends CrudRepository<Question, Long> {

    List<Question> findByQuizAndNumberGreaterThanOrderByNumberAsc(Quiz quiz, Integer number);

    List<Question> findByQuizOrderByNumberAsc(Quiz quiz);
}
