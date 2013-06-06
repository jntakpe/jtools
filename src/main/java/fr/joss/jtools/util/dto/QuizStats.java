package fr.joss.jtools.util.dto;

/**
 * Objet permettant d'afficher les résultats des différents quiz
 *
 * @author jntakpe
 */
public class QuizStats {

    String title;

    Integer score;

    Integer nbExec;

    Integer meanResult;

    BestScore bestScore;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getNbExec() {
        return nbExec;
    }

    public void setNbExec(Integer nbExec) {
        this.nbExec = nbExec;
    }

    public Integer getMeanResult() {
        return meanResult;
    }

    public void setMeanResult(Integer meanResult) {
        this.meanResult = meanResult;
    }

    public BestScore getBestScore() {
        return bestScore;
    }

    public void setBestScore(BestScore bestScore) {
        this.bestScore = bestScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuizStats quizStats = (QuizStats) o;

        if (!title.equals(quizStats.title)) return false;

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
