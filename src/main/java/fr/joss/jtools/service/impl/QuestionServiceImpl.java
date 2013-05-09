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
}
