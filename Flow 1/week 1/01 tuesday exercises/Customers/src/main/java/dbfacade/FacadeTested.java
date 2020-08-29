package dbfacade;

import entity.Customer;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Nicklas Nielsen
 */
public class FacadeTested {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        CustomerFacade cf = CustomerFacade.getCustomerFacade(emf);

        // findByID
        System.out.println("--- findByID ---");

        int id = 1;
        Customer customer = cf.findByID(id);

        System.out.println(customer);

        // findByLastName
        System.out.println("--- findByLastName ---");

        String lastName = "Larsen";
        List<Customer> customers = cf.findByLastName(lastName);

        System.out.println(customers);

        // getNumberOfCustomers
        System.out.println("--- getNumberOfCustomers ---");

        int number = cf.getNumberOfCustomers();

        System.out.println(number);

        // allCustomers
        System.out.println("--- allCustomers ---");

        customers = cf.allCustomers();

        System.out.println(customers);

        // addCustomer
        System.out.println("--- addCustomer ---");

        customer = cf.addCustomer("Test", "Mand");

        System.out.println(customer);
    }
}
