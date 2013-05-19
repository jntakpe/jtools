package fr.joss.jtools.util;

import fr.joss.jtools.domain.Question;

/**
 * Message de réponse à la réponse d'une question de quiz
 *
 * @author jntakpe
 */
public class ResponseQuestion {

    private final boolean correctAnswer;

    private final String explanation;

    private Question question;

    public ResponseQuestion(Question question, boolean correctAnswer, String explanation) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

}
