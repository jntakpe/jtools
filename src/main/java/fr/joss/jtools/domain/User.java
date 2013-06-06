package fr.joss.jtools.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.joss.jtools.util.constants.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant un utilisateur
 *
 * @author jntakpe
 */
@Entity
@Table(name = "users")
@SequenceGenerator(name = "SG", sequenceName = "SEQ_USER")
public class User extends GenericDomain {

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date birthdate;

    @Column(nullable = false, unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Date lastAccess;

    @JsonIgnore
    @OneToMany(mappedBy = "quizUserId.user", cascade = CascadeType.REMOVE)
    private Set<QuizUser> quizUsers = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "questionUserId.user", cascade = CascadeType.REMOVE)
    private Set<QuestionUser> questionUsers = new HashSet<>();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Set<QuizUser> getQuizUsers() {
        return quizUsers;
    }

    public void setQuizUsers(Set<QuizUser> quizUsers) {
        this.quizUsers = quizUsers;
    }

    public Set<QuestionUser> getQuestionUsers() {
        return questionUsers;
    }

    public void setQuestionUsers(Set<QuestionUser> questionUsers) {
        this.questionUsers = questionUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!email.equals(user.email)) return false;
        if (!login.equals(user.login)) return false;
        if (!phone.equals(user.phone)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + phone.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return login;
    }
}
