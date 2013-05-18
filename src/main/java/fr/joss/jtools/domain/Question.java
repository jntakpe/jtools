package fr.joss.jtools.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant la question d'un quiz
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "SEQ_QUESTION")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"number", "quiz_id"}))
public class Question extends GenericDomain {

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private String firstAnswer;

    @Column(nullable = false)
    private String secondAnswer;

    private String thirdAnswer;

    private String fourthAnswer;

    @Column(nullable = false)
    private Integer correctAnswer;

    private String explanation;

    @Column(nullable = false)
    private Integer duration;

    @JsonIgnore
    @ManyToOne
    private Quiz quiz;

    @JsonIgnore
    @OneToMany(mappedBy = "questionUserId.question")
    private Set<QuestionUser> questionUser = new HashSet<>();

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public void setThirdAnswer(String thirdAnswer) {
        this.thirdAnswer = thirdAnswer;
    }

    public String getFourthAnswer() {
        return fourthAnswer;
    }

    public void setFourthAnswer(String fourthAnswer) {
        this.fourthAnswer = fourthAnswer;
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Set<QuestionUser> getQuestionUser() {
        return questionUser;
    }

    public void setQuestionUser(Set<QuestionUser> questionUser) {
        this.questionUser = questionUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;

        Question question = (Question) o;

        if (!number.equals(question.number)) return false;
        if (!quiz.equals(question.quiz)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = number.hashCode();
        result = 31 * result + quiz.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return number.toString() + " du quiz : " + quiz;
    }
}