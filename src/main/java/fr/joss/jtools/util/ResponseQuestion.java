package fr.joss.jtools.util;

import fr.joss.jtools.domain.Question;

/**
 * Message de réponse à la réponse d'une question de quiz
 *
 * @author jntakpe
 */
public class ResponseQuestion {

    private final boolean goodAnswer;

    private final Integer correctAnswer;

    private final String explanation;

    private Question question;

    public ResponseQuestion(Question question, Integer correctAnswer, boolean goodAnswer, String explanation) {
        this.question = question;
        this.goodAnswer = goodAnswer;
        this.explanation = explanation;
        this.correctAnswer = correctAnswer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isGoodAnswer() {
        return goodAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }
}
