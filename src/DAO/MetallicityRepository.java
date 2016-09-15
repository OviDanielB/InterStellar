package DAO;

import Model.Metallicity;
import utils.EntityManagerSingleton;

import javax.persistence.EntityManager;

/**
 * Manages interaction with DB and Metallicity entity
 */
public class MetallicityRepository {

    /**
     * persist metallicity
     * @param metallicity
     */
    public static void save(Metallicity metallicity){
        EntityManager entityManager = EntityManagerSingleton.getEntityManager();

        entityManager.persist(metallicity);

    }
}
