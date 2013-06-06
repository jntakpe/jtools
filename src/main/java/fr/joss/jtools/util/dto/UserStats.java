package fr.joss.jtools.util.dto;

/**
 * Objet permettant d'afficher les résultats des utilisateurs sur les différents quiz
 *
 * @author jntakpe
 */
public class UserStats {

    private String login;

    private int nbQuiz;

    private int meanResult;

    private int bestResultScore;

    private String bestResultQuiz;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getNbQuiz() {
        return nbQuiz;
    }

    public void setNbQuiz(int nbQuiz) {
        this.nbQuiz = nbQuiz;
    }

    public int getMeanResult() {
        return meanResult;
    }

    public void setMeanResult(int meanResult) {
        this.meanResult = meanResult;
    }

    public int getBestResultScore() {
        return bestResultScore;
    }

    public void setBestResultScore(int bestResultScore) {
        this.bestResultScore = bestResultScore;
    }

    public String getBestResultQuiz() {
        return bestResultQuiz;
    }

    public void setBestResultQuiz(String bestResultQuiz) {
        this.bestResultQuiz = bestResultQuiz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserStats userStats = (UserStats) o;

        if (!login.equals(userStats.login)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public String toString() {
        return login;
    }
}
