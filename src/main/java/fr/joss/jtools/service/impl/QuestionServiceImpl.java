package fr.joss.jtools.service.impl;

import fr.joss.jtools.domain.Question;
import fr.joss.jtools.domain.Quiz;
import fr.joss.jtools.repository.QuestionRepository;
import fr.joss.jtools.service.QuestionService;
import fr.joss.jtools.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
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
    private QuestionRepository questionRepository;

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
    public Long[] findByQuizOrderByNumberAsc(Long quizId) {
        List<Question> questions = questionRepository.findByQuizOrderByNumberAsc(quizService.findOne(quizId));
        int size = questions.size();
        Long[] ids = new Long[size];
        for (int i = 0; i < size; i++) {
            ids[i] = questions.get(i).getId();
        }
        return ids;
    }
}
