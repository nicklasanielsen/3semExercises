package facades;

import dto.CustomerDTO;
import entities.BankCustomer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *  
 * @author Nicklas Nielsen
 */
public class CustomerFacadeTest {

    private static EntityManagerFactory emf = null;
    private static CustomerFacade facade = null;
    private List<BankCustomer> bankCustomers = new ArrayList();
    
    @BeforeAll
    public static void setUpClass(){
        emf = Persistence.createEntityManagerFactory("pu");
        facade = CustomerFacade.getCustomerFacade(emf);
    }
    
    @BeforeEach
    public void setUp(){
        bankCustomers.clear();
        bankCustomers.add(new BankCustomer("Nicklas", "Nielsen", "DK000001", 192754.2, 1, "Noget vigtigt"));
        bankCustomers.add(new BankCustomer("Mathias", "Haugaard", "DK000002", 2714.2, 5, "Vigtig info"));
        bankCustomers.add(new BankCustomer("Nikolaj", "Larsen", "DK000003", 0.0, 3, "Vigtig kunde"));
        
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("BankCustomer.deleteAllRows");
        
        em.getTransaction().begin();
        query.executeUpdate();
        em.persist(bankCustomers.get(0));
        em.persist(bankCustomers.get(1));
        em.persist(bankCustomers.get(2));
        em.getTransaction().commit();
        
        em.close();
    }
    
    @AfterAll
    public static void clearClass(){
        emf.close();
    }

    @Test
    public void testGetCustomerByID(){
        // Arrange
        BankCustomer bankCustomer = bankCustomers.get(2);
        CustomerDTO expected = new CustomerDTO(bankCustomer);
        
        // Act
        CustomerDTO actual = facade.getCustomerByID(expected.getCustomerID());
        
        // Assert
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetCustomerByName(){
        // Arrange
        String name = bankCustomers.get(1).getFirstName();
        CustomerDTO customerDTO = new CustomerDTO(bankCustomers.get(1));
        List<CustomerDTO> expected = new ArrayList();
        expected.add(customerDTO);
        
        // Act
        List<CustomerDTO> actual = facade.getCustomerByName(name);
        
        // Assert
        assertEquals(expected, actual);
    }
    
    @Test
    public void testAddCustomer(){
        // Arrange
        BankCustomer expected = new BankCustomer("FirstTest", "LastTest", "TestNumber", 123, 1, "Testing internal information");
        
        // Act
        BankCustomer actual = facade.addCustomer(expected);
        
        // Assert
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetAllBankCustomers(){
        // Arrange
        List<BankCustomer> expected = bankCustomers;
        
        // Act
        List<BankCustomer> actual = facade.getAllBankCustomers();
        
        // Assert
        assertTrue(expected.containsAll(actual));
    }
}
