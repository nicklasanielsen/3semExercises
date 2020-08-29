package rest;

import com.google.gson.Gson;
import dto.CustomerDTO;
import entities.BankCustomer;
import facades.CustomerFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author Nicklas Nielsen
 */
@Path("bankcustomer")
public class BankCustomerResource {

    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("pu");
    private static final CustomerFacade FACADE = CustomerFacade.getCustomerFacade(EMF);
            
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getById(@PathParam("id") int id){
        CustomerDTO customerDTO = FACADE.getCustomerByID(id);
        
        return new Gson().toJson(customerDTO);
    }
    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll(){
        List<BankCustomer> bankCustomers = FACADE.getAllBankCustomers();
        
        return new Gson().toJson(bankCustomers);
    }
}
