package fr.joss.jtools.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entité représentant la réponse d'un utisateur à une question
 *
 * @author jntakpe
 */
@Entity
@Table(name = "question_user")
@AssociationOverrides({
        @AssociationOverride(name = "questionUserId.user",
                joinColumns = @JoinColumn(referencedColumnName = "id", nullable = false, updatable = false)),
        @AssociationOverride(name = "questionUserId.question",
                joinColumns = @JoinColumn(referencedColumnName = "id", nullable = false, updatable = false))})
public class QuestionUser implements Serializable {

    @EmbeddedId
    private QuestionUserId questionUserId = new QuestionUserId();

    @Column(nullable = false)
    private Integer answer;

    @Transient
    public Question getQuestion() {
        return questionUserId.getQuestion();
    }

    public void setQuestion(Question question) {
        questionUserId.setQuestion(question);
    }

    @Transient
    public User getUser() {
        return questionUserId.getUser();
    }

    public void setUser(User user) {
        questionUserId.setUser(user);
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionUser)) return false;

        QuestionUser that = (QuestionUser) o;

        if (!questionUserId.equals(that.questionUserId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return questionUserId.hashCode();
    }

    @Override
    public String toString() {
        return "QuestionUser{" +
                "questionUserId=" + questionUserId +
                '}';
    }
}
