package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Singleton class to access JPA entity manager
 */
public class EntityManagerSingleton {

    // private attributes (as by Singleton pattern)
    private static EntityManager entityManager;
    private static EntityManagerFactory factory;

    // private constructor (as by Singleton pattern)
    private EntityManagerSingleton() {
        factory = Persistence.createEntityManagerFactory("InterStellar");
        entityManager = factory.createEntityManager();
    }

    // return the only instance (singleton)
    public static EntityManager getEntityManager(){
        if(entityManager == null){
             new EntityManagerSingleton();
        }
        return entityManager;
    }

    // close when needed no more
    public void close(){
        entityManager.close();
        factory.close();
    }
}
