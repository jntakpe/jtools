package fr.joss.jtools.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.util.Date;

/**
 * Entité représentant un film (depuis suppression accès Google Drive)
 *
 * @author jntakpe
 */
@Entity
@SequenceGenerator(name = "SG", sequenceName = "SEQ_MOVIE")
public class Movie extends GenericDomain {

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String director;

    @ManyToOne(optional = false)
    private User addedBy;

    @Column(nullable = false)
    private Date addDate;

    @Column(nullable = false)
    private Integer ratings;

    private boolean jossSawIt = false;

    private boolean selrakSawIt = false;

    private boolean jujupiwiSawIt = false;

    private boolean ameliaSawIt = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Integer getRatings() {
        return ratings;
    }

    public void setRatings(Integer ratings) {
        this.ratings = ratings;
    }

    public boolean isJossSawIt() {
        return jossSawIt;
    }

    public void setJossSawIt(boolean jossSawIt) {
        this.jossSawIt = jossSawIt;
    }

    public boolean isSelrakSawIt() {
        return selrakSawIt;
    }

    public void setSelrakSawIt(boolean selrakSawIt) {
        this.selrakSawIt = selrakSawIt;
    }

    public boolean isJujupiwiSawIt() {
        return jujupiwiSawIt;
    }

    public void setJujupiwiSawIt(boolean jujupiwiSawIt) {
        this.jujupiwiSawIt = jujupiwiSawIt;
    }

    public boolean isAmeliaSawIt() {
        return ameliaSawIt;
    }

    public void setAmeliaSawIt(boolean ameliaSawIt) {
        this.ameliaSawIt = ameliaSawIt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (!title.equals(movie.title)) return false;

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
