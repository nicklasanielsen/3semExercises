package facades;

import entities.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * 
 * @author Nicklas Nielsen
 */
public class EmployeeFacade {
    
    private static EntityManagerFactory emf;
    private static EmployeeFacade instance;
    
    private EmployeeFacade(){
    }
    
    public static EmployeeFacade getEmployeeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public Employee getEmployeeById(int id){
        EntityManager em = getEntityManager();
        
        try {
            Employee employee = em.find(Employee.class, id);
            
            return employee;
        } finally {
            em.close();
        }
    }
    
    public List<Employee> getEmployeesByName(String name){
        EntityManager em = getEntityManager();
        
        try{
            Query query = em.createQuery("SELECT e FROM Employee e WHERE e.name = :name");
            query.setParameter("name", name);
            List<Employee> employees = query.getResultList();
            
            return employees;
        } finally {
            em.close();
        }
    }
    public List<Employee> getAllEmployees(){
        EntityManager em = getEntityManager();
        
        try{
            Query query = em.createQuery("SELECT e FROM Employee e");
            List<Employee> employees = query.getResultList();
            System.out.println(employees);
            return employees;
        } finally {
            em.close();
        }
    }
    public List<Employee> getEmployeesWithHighestSalary(){
        EntityManager em = getEntityManager();
        
        try{
            Query query = em.createQuery("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(e.salary) FROM Employee e)");
            List<Employee> employees = query.getResultList();
            
            return employees;
        } finally {
            em.close();
        }
    }
    public Employee createEmployee(String name, String address, int salary){
        Employee employee = new Employee(name, address, salary);
        EntityManager em = getEntityManager();
        
        try{
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
            
            return employee;
        }finally {
            em.close();
        }
    }    
}
