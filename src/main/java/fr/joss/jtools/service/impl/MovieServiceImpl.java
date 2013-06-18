package fr.joss.jtools.service.impl;

import fr.joss.jtools.domain.Movie;
import fr.joss.jtools.repository.MovieRepository;
import fr.joss.jtools.service.MovieService;
import fr.joss.jtools.service.UserService;
import fr.joss.jtools.util.constants.MovieUsers;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation des services associés à l'entité {@link Movie}
 *
 * @author jntakpe
 * @see GenericServiceImpl
 */
@Service
public class MovieServiceImpl extends GenericServiceImpl<Movie> implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserService userService;

    @Override
    protected CrudRepository<Movie, Long> getRepository() {
        return movieRepository;
    }

    @Override
    @Transactional
    public Movie save(Movie movie) {
        if (movie.getId() == null) {
            movie.setAddDate(Instant.now().toDate());
            movie.setAddedBy(userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
        } else {
            Movie old = findOne(movie.getId());
            movie.setAddedBy(old.getAddedBy());
            movie.setAddDate(old.getAddDate());
            movie.setAmeliaSawIt(old.isAmeliaSawIt());
            movie.setSelrakSawIt(old.isSelrakSawIt());
            movie.setJossSawIt(old.isJossSawIt());
            movie.setJujupiwiSawIt(old.isJujupiwiSawIt());
        }
        return super.save(movie);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTitleAvailable(Long id, String title) {
        Movie movie = movieRepository.findByTitle(title);
        return movie == null || movie.getId().equals(id);
    }

    @Override
    @Transactional
    public void switchSaw(Long id, String username, boolean value) {
        Movie movie = findOne(id);
        switch (MovieUsers.valueOf(username.toUpperCase())) {
            case AMELIA:
                movie.setAmeliaSawIt(value);
                break;
            case CHARLES:
                movie.setSelrakSawIt(value);
                break;
            case JULIEN:
                movie.setJujupiwiSawIt(value);
                break;
            case JOCELYN:
                movie.setJossSawIt(value);
                break;
        }
    }
}
