package fr.joss.jtools.service;

import fr.joss.jtools.domain.GenericDomain;

/**
 * Interface fournissant les services usuels
 *
 * @author jntakpe
 */
public interface GenericService<T extends GenericDomain> {

    /**
     * Compte le nombre d'entités dans une table
     *
     * @return nombre d'enregistrement
     */
    long count();

    /**
     * Retrouve une entité à l'aide de l'identifiant de la table
     *
     * @param id identifiant de la ligne
     * @return l'entité possédant cette id
     */
    T findOne(Long id);

    /**
     * Retrouve toutes les entités de la table
     *
     * @return entités de la table
     */
    Iterable<T> findAll();

    /**
     * Indique si l'entité existe en table
     *
     * @param id identifiant de l'entité
     * @return true si exist
     */
    boolean exists(Long id);

    /**
     * Supprime l'entité ayant cet identifiant
     *
     * @param id id de l'entité
     */
    void delete(Long id);

    /**
     * Supprime l'entité
     *
     * @param t entité à supprimer
     */
    void delete(T t);

    /**
     * Sauvegarde l'entité. Si elle n'existe pas fait un 'persist' sinon un 'merge'.
     * Seule l'entité renvoyée est 'managed', celle  passée en paramètre est 'detached'.
     * En d'autres termes, toutes les modifications effectuées sur l'entité renvoyée par la fonction seront persistées
     * à la fin de la transaction alors que celles effectuées sur l'objet passé en paramètre ne seront pas persistées.
     *
     * @param t entité à sauvegarder
     * @return l'entité 'managed'. Attention ce n'est pas le même objet que celui passé en paramètre.
     */
    T save(T t);
}
