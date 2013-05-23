package fr.joss.jtools.service.impl;

import com.google.common.collect.Lists;
import fr.joss.jtools.domain.*;
import fr.joss.jtools.repository.QuestionUserRepository;
import fr.joss.jtools.repository.QuizRepository;
import fr.joss.jtools.repository.QuizUserRepository;
import fr.joss.jtools.service.QuizService;
import fr.joss.jtools.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

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


    @Autowired
    private QuestionUserRepository questionUserRepository;

    @Autowired
    private QuizUserRepository quizUserRepository;

    @Override
    protected CrudRepository<Quiz, Long> getRepository() {
        return quizRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTitleAvailable(Long id, String title) {
        Quiz quiz = quizRepository.findByTitleIgnoreCase(title);
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

    @Override
    @Transactional(readOnly = true)
    public Quiz findOne(Long id) {
        Quiz quiz = super.findOne(id);
        quiz.setTotalQuestion(quiz.getQuestions().size());
        return quiz;
    }

    @Override
    @Transactional
    public QuizUser saveResult(Long quizId) {
        Quiz quiz = findOne(quizId);
        quiz.setExecNumber(quiz.getExecNumber() + 1);
        User user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        Set<Question> questions = quiz.getQuestions();
        int nbCorrectAnswer = 0;
        for (Question question : questions) {
            QuestionUserId questionUserId = new QuestionUserId();
            questionUserId.setQuestion(question);
            questionUserId.setUser(user);
            QuestionUser questionUser = questionUserRepository.findOne(questionUserId);
            if (questionUser.getAnswer().equals(question.getCorrectAnswer()))
                nbCorrectAnswer++;
        }
        QuizUser quizUser = new QuizUser();
        quizUser.setUser(user);
        quizUser.setQuiz(quiz);
        quizUser.setResult(nbCorrectAnswer * 100 / questions.size());
        return quizUserRepository.save(quizUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer averageQuizResult(Quiz quiz) {
        List<Integer> results = quizUserRepository.getAllQuizResult(quiz);
        int sum = 0;
        for (Integer result : results) {
            sum += result;
        }
        return sum / results.size();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Quiz> findUndoneQuiz(User user) {
        List<Quiz> allQuiz = Lists.newArrayList(findAll());
        List<Quiz> doneQuiz = quizUserRepository.getAllDoneQuiz(user);
        return new ArrayList<Quiz>(CollectionUtils.subtract(allQuiz, doneQuiz));

    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasDone(User user, Long quizId) {
        QuizUserId quizUserId = new QuizUserId();
        quizUserId.setUser(user);
        quizUserId.setQuiz(findOne(quizId));
        return quizUserRepository.exists(quizUserId);
    }
}
