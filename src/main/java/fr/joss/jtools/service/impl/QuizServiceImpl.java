package fr.joss.jtools.service.impl;

import fr.joss.jtools.domain.Quiz;
import fr.joss.jtools.domain.User;
import fr.joss.jtools.repository.QuizRepository;
import fr.joss.jtools.service.QuizService;
import fr.joss.jtools.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Implémentation des services associés à l'entité {@link Quiz}
 *
 * @author jntakpe
 * @see GenericServiceImpl
 */
@Service
public class QuizServiceImpl extends GenericServiceImpl<Quiz> implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserService userService;

    @Override
    protected CrudRepository<Quiz, Long> getRepository() {
        return quizRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTitleAvailable(Long id, String title) {
        Quiz quiz = quizRepository.findByTitle(title);
        return quiz == null || quiz.getId().equals(id);
    }

    @Override
    @Transactional
    public Quiz save(Quiz quiz) {
        if (quiz.getId() == null)
            quiz.setCreateDate(Calendar.getInstance().getTime());
        quiz.setCreator(userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
        return super.save(quiz);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Quiz> findByCreator(User creator) {
        return quizRepository.findByCreator(creator);
    }

}
