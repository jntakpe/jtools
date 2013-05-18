package fr.joss.jtools.repository;

import fr.joss.jtools.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface de manipulation de l'entité {@link User}. Implémentation générée par Spring Data JPA.
 *
 * @author jntakpe
 * @see CrudRepository
 */
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE UPPER(u.login) = UPPER(?1)")
    User findByLogin(String login);

    User findByEmail(String email);

    User findByPhone(String phone);
}
