package fr.joss.jtools.service.impl;

import fr.joss.jtools.domain.*;
import fr.joss.jtools.repository.QuestionRepository;
import fr.joss.jtools.repository.QuestionUserRepository;
import fr.joss.jtools.service.QuestionService;
import fr.joss.jtools.service.QuizService;
import fr.joss.jtools.service.UserService;
import fr.joss.jtools.util.ResponseQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation des services associés à l'entité {@link Question}
 *
 * @author jntakpe
 * @see GenericServiceImpl
 */
@Service
public class QuestionServiceImpl extends GenericServiceImpl<Question> implements QuestionService {

    @Autowired
    protected UserService userService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionUserRepository questionUserRepository;

    @Autowired
    private QuizService quizService;

    @Override
    protected CrudRepository<Question, Long> getRepository() {
        return questionRepository;
    }

    @Override
    @Transactional
    public Question save(Question question, Long quizId) {
        Quiz quiz = quizService.findOne(quizId);
        question.setQuiz(quiz);
        return super.save(question);
    }

    @Override
    @Transactional
    public void updateNumber(Quiz quiz, Integer number) {
        List<Question> questions = questionRepository.findByQuizAndNumberGreaterThanOrderByNumberAsc(quiz, number);
        for (Question question : questions) {
            question.setNumber(question.getNumber() - 1);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> findSortedIds(Long quizId) {
        return questionRepository.findSortedIds(quizService.findOne(quizId));
    }

    @Override
    @Transactional
    public ResponseQuestion validCurrentGetNext(Long id, Integer answer) {
        Question currentQuestion = findOne(id);
        QuestionUser questionUser = new QuestionUser();
        questionUser.setQuestion(currentQuestion);
        questionUser.setUser(userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
        questionUser.setAnswer(answer);
        questionUserRepository.save(questionUser);
        Quiz quiz = currentQuestion.getQuiz();
        Question nextQuestion = questionRepository.findByQuizAndNumber(quiz, currentQuestion.getNumber() + 1);
        ResponseQuestion response = new ResponseQuestion(nextQuestion, currentQuestion.getCorrectAnswer(),
                answer.equals(currentQuestion.getCorrectAnswer()), currentQuestion.getExplanation());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public Question findLastQuestion(Quiz quiz) {
        User user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        QuizUserId quizUserId = new QuizUserId();
        quizUserId.setQuiz(quiz);
        quizUserId.setUser(user);
        List<Question> questions = quiz.getQuestions();
        for (Question question : questions) {
            QuestionUserId questionUserId = new QuestionUserId();
            questionUserId.setUser(user);
            questionUserId.setQuestion(question);
            QuestionUser questionUser = questionUserRepository.findOne(questionUserId);
            if (questionUser == null)
                return question;
        }
        return quiz.getQuestions().get(0);
    }
}
