package fr.joss.jtools.repository;

import fr.joss.jtools.domain.Movie;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface de manipulation de l'entité {@link Movie}
 *
 * @author jntakpe
 * @see CrudRepository
 */
public interface MovieRepository extends CrudRepository<Movie, Long> {

}
