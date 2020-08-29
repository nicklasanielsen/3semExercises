package dbfacade;

import entity.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Nicklas Nielsen
 */
public class CustomerFacade {

    private static EntityManagerFactory emf = null;
    private static CustomerFacade instance = null;

    private CustomerFacade() {
    }

    public static CustomerFacade getCustomerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }

    public Customer findByID(int id) {
        EntityManager em = emf.createEntityManager();

        try {
            Customer Customer = em.find(Customer.class, id);
            return Customer;
        } finally {
            em.close();
        }
    }

    public List<Customer> findByLastName(String name) {
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createQuery("SELECT c FROM Customer c WHERE c.lastName = :name");
            query.setParameter("name", name);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public int getNumberOfCustomers() {
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createQuery("SELECT COUNT(c) FROM Customer c");
            return Integer.parseInt(query.getSingleResult().toString());
        } finally {
            em.close();
        }
    }

    public List<Customer> allCustomers() {
        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createQuery("SELECT c FROM Customer c");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Customer addCustomer(String fName, String lName) {
        Customer customer = new Customer(fName, lName);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();

            return customer;
        } finally {
            em.close();
        }
    }
}
