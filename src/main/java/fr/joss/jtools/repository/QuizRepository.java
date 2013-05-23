package fr.joss.jtools.repository;

import fr.joss.jtools.domain.Quiz;
import fr.joss.jtools.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Interface de manipulation de l'entité {@link Quiz}. Implémentation générée par Spring Data JPA.
 *
 * @author jntakpe
 * @see CrudRepository
 */
public interface QuizRepository extends CrudRepository<Quiz, Long> {

    Quiz findByTitleIgnoreCase(String title);

    List<Quiz> findByCreator(User creator);
}
