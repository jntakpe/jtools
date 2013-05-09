package fr.joss.jtools.domain;

import javax.persistence.*;

/**
 * Entité représentant l'éxécution d'un quiz
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "SEQ_QUIZ_USER")
@AssociationOverrides({
        @AssociationOverride(name = "quizUserId.user", joinColumns = @JoinColumn(name = "id", updatable = false)),
        @AssociationOverride(name = "quizUserId.quiz", joinColumns = @JoinColumn(name = "id", updatable = false))})
public class QuizUser extends GenericDomain {

    @EmbeddedId
    private QuizUserId quizUserId = new QuizUserId();

    private Integer result;

    @Column(nullable = false)
    private Integer count;

    @Transient
    public Quiz getQuiz() {
        return quizUserId.getQuiz();
    }

    public void setQuiz(Quiz quiz) {
        quizUserId.setQuiz(quiz);
    }

    @Transient
    public User getUser() {
        return quizUserId.getUser();
    }

    public void setUser(User user) {
        quizUserId.setUser(user);
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizUser)) return false;

        QuizUser quizUser = (QuizUser) o;

        if (!count.equals(quizUser.count)) return false;
        if (!quizUserId.equals(quizUser.quizUserId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = quizUserId.hashCode();
        result = 31 * result + count.hashCode();
        return result;
    }
}
