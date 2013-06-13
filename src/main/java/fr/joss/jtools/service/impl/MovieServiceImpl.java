package fr.joss.jtools.service.impl;

import fr.joss.jtools.domain.Movie;
import fr.joss.jtools.repository.MovieRepository;
import fr.joss.jtools.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

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

    @Override
    protected CrudRepository<Movie, Long> getRepository() {
        return movieRepository;
    }
}
