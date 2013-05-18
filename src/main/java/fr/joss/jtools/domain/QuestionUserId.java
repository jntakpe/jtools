package fr.joss.jtools.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Clé composite de la table QuestionUser
 *
 * @author jntakpe
 */
@Embeddable
public class QuestionUserId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionUserId)) return false;

        QuestionUserId that = (QuestionUserId) o;

        if (!question.equals(that.question)) return false;
        if (!user.equals(that.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + question.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "QuestionUserId{" +
                "user=" + user +
                ", question=" + question +
                '}';
    }
}
