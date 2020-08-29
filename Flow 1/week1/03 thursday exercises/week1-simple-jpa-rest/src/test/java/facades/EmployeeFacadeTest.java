package facades;

import entities.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Nicklas Nielsen
 */
public class EmployeeFacadeTest {
    
    private static EntityManagerFactory emf = null;
    private static EmployeeFacade ef = null;
    private List<Employee> employees = new ArrayList();

    @BeforeAll
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("pu");
        ef = EmployeeFacade.getEmployeeFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        employees.clear();
        employees.add(new Employee("Nicklas", "Sydhavnen", 1000));
        employees.add(new Employee("Mathias", "Lyngby", 500));
        employees.add(new Employee("Nikolaj", "Landet", 125));

        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("DELETE FROM Employee e");

        em.getTransaction().begin();
        query.executeUpdate();
        em.persist(employees.get(0));
        em.persist(employees.get(1));
        em.persist(employees.get(2));
        em.getTransaction().commit();

        em.close();
    }
    
    @AfterAll
    public static void clearClass() {
        emf.close();
    }
    
    @Test
    public void testGetEmployeeById(){
        // Arrange
        Employee expected = employees.get(2);
        
        // Act
        Employee actual = ef.getEmployeeById(expected.getId());
        
        // Assert
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetEmployeeByName(){
        // Arrange
        String name = "Nicklas";
        List<Employee> expected = new ArrayList();
        expected.add(employees.get(0));
        
        // Act
        List<Employee> actual = ef.getEmployeesByName(name);
        
        // Assert
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetAllEmployees(){
        // Arrange
        List<Employee> expected = employees;
        
        // Act
        List<Employee> actual = ef.getAllEmployees();
        
        // Assert
        assertTrue(expected.containsAll(actual));
    }
    
    @Test
    public void testGetEmployeesWithHighestSalary(){
        // Arrange
        List<Employee> expected = new ArrayList();
        expected.add(employees.get(0));
        
        // Act
        List<Employee> actual = ef.getEmployeesWithHighestSalary();
        
        // Assert
        assertEquals(expected, actual);
    }
    
    @Test
    public void testCreateEmployee(){
        // Arrange
        String name = "TestMand";
        String address = "TestLand";
        int salary = 123456789;
        Employee expected = new Employee(name, address, salary);
        
        // Act
        Employee actual = ef.createEmployee(name, address, salary);
        
        // Assert
        assertEquals(expected, actual);
    }
}
