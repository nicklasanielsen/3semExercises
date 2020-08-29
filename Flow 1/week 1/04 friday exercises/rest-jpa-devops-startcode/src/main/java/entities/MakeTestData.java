package entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Nicklas Nielsen
 */
public class MakeTestData {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        
        BankCustomer bankCustomer1 = new BankCustomer("Nicklas", "Nielsen", "DK000001", 192754.2, 1, "Noget vigtigt");
        BankCustomer bankCustomer2 = new BankCustomer("Mathias", "Haugaard", "DK000002", 2714.2, 5, "Vigtig info");
        BankCustomer bankCustomer3 = new BankCustomer("Nikolaj", "Larsen", "DK000003", 0.0, 3, "Vigtig kunde");
        
        em.getTransaction().begin();
        em.persist(bankCustomer1);
        em.persist(bankCustomer2);
        em.persist(bankCustomer3);
        em.getTransaction().commit();
    }
}
