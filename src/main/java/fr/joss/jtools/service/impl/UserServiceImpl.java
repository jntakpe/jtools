package fr.joss.jtools.service.impl;

import fr.joss.jtools.domain.Parameter;
import fr.joss.jtools.domain.Quiz;
import fr.joss.jtools.domain.User;
import fr.joss.jtools.exception.BusinessCode;
import fr.joss.jtools.exception.BusinessException;
import fr.joss.jtools.repository.QuizRepository;
import fr.joss.jtools.repository.UserRepository;
import fr.joss.jtools.service.ParameterService;
import fr.joss.jtools.service.UserService;
import fr.joss.jtools.util.constants.MandatoryParams;
import fr.joss.jtools.util.constants.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * Implémentation des services associés à l'entité {@link User}
 *
 * @author jntakpe
 * @see GenericServiceImpl
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public CrudRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = findByLogin(login);
        if (user == null)
            throw new UsernameNotFoundException("Impossible de retrouver l'utilisateur '" + login + "'.");
        user.setLastAccess(Calendar.getInstance().getTime());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        return userRepository.findByLoginIgnoreCase(login);
    }

    @Override
    @Transactional
    public User save(User user) {
        String firstChar = user.getFirstName().substring(0, 1).toUpperCase();
        user.setFirstName(firstChar + user.getFirstName().substring(1).toLowerCase());
        user.setLastName(user.getLastName().toUpperCase());
        user.setEmail(user.getEmail().toLowerCase());
        if (user.getId() == null) {
            user.setLastAccess(Calendar.getInstance().getTime());
        } else if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(findOne(user.getId()).getPassword());
        }
        if (user.getRole() == null)
            user.setRole(Role.ROLE_USER);
        return super.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isLoginAvaillable(Long id, String login) {
        User user = findByLogin(login);
        return user == null || user.getId().equals(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEmailAvaillable(Long id, String email) {
        User user = userRepository.findByEmail(email.toLowerCase());
        return user == null || user.getId().equals(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isPhoneAvaillable(Long id, String phone) {
        User user = userRepository.findByPhone(phone);
        return user == null || user.getId().equals(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean sendUserInfo(String email, String login) {
        User user = null;
        if (!StringUtils.isBlank(email)) {
            user = userRepository.findByEmail(email);
        } else if (!StringUtils.isBlank(login)) {
            user = userRepository.findByLoginIgnoreCase(login);
        }
        if (user == null)
            return false;
        Parameter smtpHost = parameterService.findByKey(MandatoryParams.SMTP_HOST.getKey());
        if (smtpHost == null || StringUtils.isBlank(smtpHost.getValue()))
            throw new BusinessException(BusinessCode.EMAIL_MISSING_PARAM, MandatoryParams.SMTP_HOST.getKey());
        Parameter smtpPort = parameterService.findByKey(MandatoryParams.SMTP_PORT.getKey());
        if (smtpPort == null || StringUtils.isBlank(smtpPort.getValue()))
            throw new BusinessException(BusinessCode.EMAIL_MISSING_PARAM, MandatoryParams.SMTP_PORT.getKey());
        Parameter smtpFrom = parameterService.findByKey(MandatoryParams.SMTP_FROM.getKey());
        if (smtpFrom == null || StringUtils.isBlank(smtpFrom.getValue()))
            throw new BusinessException(BusinessCode.EMAIL_MISSING_PARAM, MandatoryParams.SMTP_FROM.getKey());

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost.getValue());
        mailSender.setPort(Integer.parseInt(smtpPort.getValue()));

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom(smtpFrom.getValue());
        mailMessage.setSubject("Mot de passe jTools");
        mailMessage.setText("Le mot de passe du compte : " + user.getLogin() + " est : " + user.getPassword());

        mailSender.send(mailMessage);
        return true;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        for (Quiz quiz : quizRepository.findByCreator(findOne(id))) {
            quiz.setCreator(null);
        }
        super.delete(id);
    }
}
