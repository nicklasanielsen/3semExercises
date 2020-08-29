package facades;

import dto.CustomerDTO;
import entities.BankCustomer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Nicklas Nielsen
 */
public class CustomerFacade {

    private static CustomerFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CustomerFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CustomerFacade getCustomerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CustomerDTO getCustomerByID(int id) {
        EntityManager em = getEntityManager();

        BankCustomer bankCustomer = em.find(BankCustomer.class, id);
        CustomerDTO customerDTO = new CustomerDTO(bankCustomer);

        return customerDTO;
    }

    // Searches for first name as it is not specified in the assignment
    public List<CustomerDTO> getCustomerByName(String name) {
        EntityManager em = getEntityManager();

        Query query = em.createQuery("SELECT b FROM BankCustomer b WHERE b.firstName = :name");
        query.setParameter("name", name);

        List<BankCustomer> bankCustomers = query.getResultList();
        List<CustomerDTO> customerDTOs = new ArrayList();
        CustomerDTO customerDTO;

        for (BankCustomer bankCustomer : bankCustomers) {
            customerDTO = new CustomerDTO(bankCustomer);
            customerDTOs.add(customerDTO);
        }

        return customerDTOs;
    }

    public BankCustomer addCustomer(BankCustomer cust) {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.persist(cust);
        em.getTransaction().commit();

        return cust;
    }

    public List<BankCustomer> getAllBankCustomers() {
        EntityManager em = getEntityManager();

        Query query = em.createQuery("SELECT b FROM BankCustomer b");
        List<BankCustomer> bankCustomers = query.getResultList();

        return bankCustomers;
    }
}
