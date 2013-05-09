package fr.joss.jtools.service;

import fr.joss.jtools.domain.Question;

/**
 * Services à la gestion de l'entité {@link Question}
 *
 * @author jntakpe
 * @see GenericService
 */
public interface QuestionService extends GenericService<Question> {

    Question save(Question question, Long quizId);
}
