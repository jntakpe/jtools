package fr.joss.jtools.service;

import fr.joss.jtools.domain.Movie;

/**
 * Services associés à l'entité {@link Movie}
 *
 * @author jntakpe
 * @see GenericService
 */
public interface MovieService extends GenericService<Movie> {

    boolean isTitleAvailable(Long id, String title);

    void switchSaw(Long id, String username, boolean value);
}
