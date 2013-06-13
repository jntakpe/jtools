package fr.joss.jtools.service.impl;

import fr.joss.jtools.domain.Parameter;
import fr.joss.jtools.repository.ParameterRepository;
import fr.joss.jtools.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation des services associés à l'entité {@link Parameter}
 *
 * @author jntakpe
 * @see GenericServiceImpl
 */
@Service
public class ParameterServiceImpl extends GenericServiceImpl<Parameter> implements ParameterService {

    @Autowired
    private ParameterRepository parameterRepository;

    @Override
    public CrudRepository<Parameter, Long> getRepository() {
        return parameterRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Parameter findByKey(String key) {
        return parameterRepository.findByKey(key);
    }


    @Override
    @Transactional(readOnly = true)
    public boolean isKeyAvailable(Long id, String key) {
        Parameter param = findByKey(key);
        return param == null || param.getId().equals(id);
    }
}
