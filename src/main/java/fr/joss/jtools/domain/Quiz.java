package fr.joss.jtools.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant un quiz
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "SEQ_QUIZ")
public class Quiz extends GenericDomain {

    @Column(unique = true, nullable = false)
    private String title;


    @Column(nullable = false)
    private Date createDate;

    @Column(nullable = false)
    private Integer execNumber = new Integer(0);

    @JsonIgnore
    @OneToMany(mappedBy = "quiz")
    private Set<Question> questions = new HashSet<>();

    @ManyToOne
    private User creator;

    @JsonIgnore
    @OneToMany(mappedBy = "quizUserId.quiz")
    private Set<QuizUser> quizUsers = new HashSet<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getExecNumber() {
        return execNumber;
    }

    public void setExecNumber(Integer execNumber) {
        this.execNumber = execNumber;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Set<QuizUser> getQuizUsers() {
        return quizUsers;
    }

    public void setQuizUsers(Set<QuizUser> quizUsers) {
        this.quizUsers = quizUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quiz)) return false;

        Quiz quiz = (Quiz) o;

        if (!title.equals(quiz.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public String toString() {
        return title;
    }
}
