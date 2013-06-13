package fr.joss.jtools.service;

import fr.joss.jtools.domain.Parameter;

/**
 * Services associés à l'entité {@link Parameter}
 *
 * @author jntakpe
 * @see GenericService
 */
public interface ParameterService extends GenericService<Parameter> {

    Parameter findByKey(String key);

    boolean isKeyAvailable(Long id, String key);

}
