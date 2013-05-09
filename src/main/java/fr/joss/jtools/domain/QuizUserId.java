package fr.joss.jtools.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Clé composite de la table QuizUser
 *
 * @author jntakpe
 */
@Embeddable
public class QuizUserId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Quiz quiz;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizUserId)) return false;

        QuizUserId that = (QuizUserId) o;

        if (!quiz.equals(that.quiz)) return false;
        if (!user.equals(that.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + quiz.hashCode();
        return result;
    }
}
