package fr.joss.jtools.service;

import fr.joss.jtools.domain.Question;
import fr.joss.jtools.domain.Quiz;
import fr.joss.jtools.util.dto.ResponseQuestion;

import java.util.List;

/**
 * Services à la gestion de l'entité {@link Question}
 *
 * @author jntakpe
 * @see GenericService
 */
public interface QuestionService extends GenericService<Question> {

    Question save(Question question, Long quizId);

    void updateNumber(Quiz quiz, Integer number);

    List<Long> findSortedIds(Long quizId);

    ResponseQuestion validCurrentGetNext(Long id, Integer answer);

    Question findLastQuestion(Quiz quiz);

}
