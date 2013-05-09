package fr.joss.jtools.repository;

import fr.joss.jtools.domain.Parameter;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface de manipulation de l'entité {@link Parameter}
 *
 * @author jntakpe
 * @see CrudRepository
 */
public interface ParameterRepository extends CrudRepository<Parameter, Long> {

    Parameter findByKey(String key);

}
