package dbfacade;

import entity.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Nicklas Nielsen
 */
public class CustomerFacadeTest {

    private static EntityManagerFactory emf = null;
    private static CustomerFacade cf = null;
    private List<Customer> customers = new ArrayList();

    @BeforeAll
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("pu");
        cf = CustomerFacade.getCustomerFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        customers.clear();
        customers.add(new Customer("Nicklas", "Nielsen"));
        customers.add(new Customer("Mathias", "Haugaard"));
        customers.add(new Customer("Nikolaj", "Larsen"));

        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("DELETE FROM Customer c");

        em.getTransaction().begin();
        query.executeUpdate();
        em.persist(customers.get(0));
        em.persist(customers.get(1));
        em.persist(customers.get(2));
        em.getTransaction().commit();

        em.close();
    }

    @AfterAll
    public static void clearClass() {
        emf.close();
    }

    @Test
    public void testFindByID() {
        // Arrange
        Customer expected = customers.get(2);

        // Act
        Customer actual = cf.findByID(expected.getId());

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByLastName() {
        // Arrange
        String name = "Haugaard";
        List<Customer> expected = new ArrayList();
        expected.add(customers.get(1));

        // Act
        List<Customer> actual = cf.findByLastName(name);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testGetNumberOfCustomers() {
        // Arrange
        int expected = customers.size();

        // Act
        int actual = cf.getNumberOfCustomers();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testAllCustomers() {
        // Arrange
        List<Customer> expected = customers;

        // Act
        List<Customer> actual = cf.allCustomers();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testAddCustomer() {
        // Arrange
        String firstName = "Peter";
        String lastName = "Parker";
        Customer expected = new Customer(firstName, lastName);

        // Act
        Customer actual = cf.addCustomer(firstName, lastName);

        // Assert
        assertEquals(expected, actual);
    }
}
