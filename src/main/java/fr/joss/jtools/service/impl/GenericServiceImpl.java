package fr.joss.jtools.service.impl;


import fr.joss.jtools.domain.GenericDomain;
import fr.joss.jtools.service.GenericService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation des services usuels
 *
 * @author jntakpe
 */
public abstract class GenericServiceImpl<T extends GenericDomain> implements GenericService<T> {

    /**
     * @return the repository to use.
     */
    protected abstract CrudRepository<T, Long> getRepository();

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long count() {
        return getRepository().count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public T findOne(Long id) {
        return getRepository().findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Iterable<T> findAll() {
        return getRepository().findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long id) {
        return getRepository().exists(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(Long id) {
        getRepository().delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(T t) {
        getRepository().delete(t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public T save(T t) {
        return getRepository().save(t);
    }

}
