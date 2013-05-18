package fr.joss.jtools.domain;

import javax.persistence.*;

/**
 * Entité représentant l'éxécution d'un quiz (relation entre un quiz et un utilisateur)
 *
 * @author jntakpe
 */
@Entity
@Table(name = "quiz_user")
@AssociationOverrides({
        @AssociationOverride(name = "quizUserId.user",
                joinColumns = @JoinColumn(referencedColumnName = "id", nullable = false, updatable = false)),
        @AssociationOverride(name = "quizUserId.quiz",
                joinColumns = @JoinColumn(referencedColumnName = "id", nullable = false, updatable = false))})
public class QuizUser {

    @EmbeddedId
    private QuizUserId quizUserId = new QuizUserId();

    @Column(nullable = false)
    private Integer result;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizUser)) return false;

        QuizUser quizUser = (QuizUser) o;

        if (!quizUserId.equals(quizUser.quizUserId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return quizUserId.hashCode();
    }

    @Override
    public String toString() {
        return "QuizUser{" +
                "quizUserId=" + quizUserId +
                '}';
    }
}
