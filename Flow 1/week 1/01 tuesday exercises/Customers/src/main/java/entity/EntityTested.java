package entity;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nicklas Nielsen
 */
public class EntityTested {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        Customer c1 = new Customer("Nicklas", "Nielsen");
        Customer c2 = new Customer("Mathias", "Haugaard");
        Customer c3 = new Customer("Nikolaj", "Larsen");

        try {
            em.getTransaction().begin();

            em.persist(c1);
            em.persist(c2);
            em.persist(c3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
