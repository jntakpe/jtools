package fr.joss.jtools.repository;

import fr.joss.jtools.domain.Quiz;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface de manipulation de l'entité {@link Quiz}. Implémentation générée par Spring Data JPA.
 *
 * @author jntakpe
 * @see CrudRepository
 */
public interface QuizRepository extends CrudRepository<Quiz, Long> {

    Quiz findByTitle(String title);

}
