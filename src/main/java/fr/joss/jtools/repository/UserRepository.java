package fr.joss.jtools.repository;

import fr.joss.jtools.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface de manipulation de l'entité {@link User}. Implémentation générée par Spring Data JPA.
 *
 * @author jntakpe
 * @see CrudRepository
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByLoginIgnoreCase(String login);

    User findByEmail(String email);

    User findByPhone(String phone);
}
