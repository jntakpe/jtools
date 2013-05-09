package fr.joss.jtools.service;

import fr.joss.jtools.domain.Age;

import java.util.Date;

/**
 * Services associés au calcul de l'âge
 *
 * @author jntakpe
 */
public interface AgeService {

    Age calcAge(Date birthdate);
}
