package fr.joss.jtools.service;

import fr.joss.jtools.domain.Quiz;

/**
 * Services à la gestion de l'entité {@link Quiz}
 *
 * @author jntakpe
 * @see GenericService
 */
public interface QuizService extends GenericService<Quiz> {

    boolean isTitleAvailable(Long id, String title);
}
