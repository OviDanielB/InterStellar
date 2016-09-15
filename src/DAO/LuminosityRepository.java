package DAO;

import Model.Luminosity;
import utils.EntityManagerSingleton;

import javax.persistence.EntityManager;

/**
 * Manages interaction with DB and Luminosity entity
 */
public class LuminosityRepository {

    /**
     * persist luminosity
     * @param luminosity
     */
    public static void save(Luminosity luminosity){
        EntityManager entityManager = EntityManagerSingleton.getEntityManager();

        entityManager.persist(luminosity);

    }
}
