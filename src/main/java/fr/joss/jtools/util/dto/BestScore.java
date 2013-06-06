package fr.joss.jtools.util.dto;

import java.util.List;

/**
 * @author jntakpe
 */
public class BestScore {

    Integer score;

    List<String> recordmen;

    public BestScore(Integer score, List<String> recordmen) {
        this.score = score;
        this.recordmen = recordmen;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<String> getRecordmen() {
        return recordmen;
    }

    public void setRecordmen(List<String> recordmen) {
        this.recordmen = recordmen;
    }
}
