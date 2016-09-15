package DAO;

import Exceptions.AlreadyPresentInDbException;
import Model.User;
import utils.EntityManagerSingleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 * Manages interaction with DB and User entity
 */
public class UserRepository {

    /**
     * stores user if not present in DB
     * @param user
     * @throws AlreadyPresentInDbException
     */
    public static void store(User user) throws AlreadyPresentInDbException {

        EntityManager manager = EntityManagerSingleton.getEntityManager();

        manager.getTransaction().begin();

        // user already exists in db
        if(manager.find(User.class, user.getUserID()) != null){
            //throw new AlreadyPresentInDbException();
            manager.getTransaction().commit();
            return;
        }

        manager.persist(user);

        manager.getTransaction().commit();
    }


    /**
     * user query by ID and password
     * @param userID
     * @param password
     * @return corrisponding user
     * @throws NoResultException
     */
    public static User getUserByIDAndPassword(String userID, String password) throws NoResultException {

        EntityManager entityManager = EntityManagerSingleton.getEntityManager();
        User user;
        try {
            user = entityManager.createQuery("SELECT u FROM User u WHERE userID = :ID AND password = :passw", User.class)
                    .setParameter("ID", userID)
                    .setParameter("passw", password)
                    .getSingleResult();

        } catch (NoResultException e){
            e.printStackTrace();
            throw e;
        }

        return user;
    }
}
