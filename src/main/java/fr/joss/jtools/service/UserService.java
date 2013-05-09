package fr.joss.jtools.service;

import fr.joss.jtools.domain.User;

/**
 * Services associés à l'entité {@link User}
 *
 * @author jntakpe
 * @see GenericService
 */
public interface UserService extends GenericService<User> {

    User findByLogin(String login);

    boolean isLoginAvaillable(Long id, String login);

    boolean isEmailAvaillable(Long id, String email);

    boolean isPhoneAvaillable(Long id, String phone);

    void sendUserInfo(User user);

}
