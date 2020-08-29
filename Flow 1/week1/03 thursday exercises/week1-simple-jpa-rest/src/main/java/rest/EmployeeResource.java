package rest;

import com.google.gson.Gson;
import dto.EmployeeDTO;
import entities.Employee;
import facades.EmployeeFacade;
import java.util.ArrayList;
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
@Path("employee")
public class EmployeeResource {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    private EmployeeFacade facade = EmployeeFacade.getEmployeeFacade(emf);

    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        List<Employee> employees = facade.getAllEmployees();
        List<EmployeeDTO> employeeDTOs = new ArrayList();
        EmployeeDTO employeeDTO;
        
        for (Employee employee : employees) {
            employeeDTO = new EmployeeDTO(employee);
            employeeDTOs.add(employeeDTO);
        }
        
        return new Gson().toJson(employeeDTOs);
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getByID(@PathParam("id") int id) {
        Employee employee = facade.getEmployeeById(id);
        EmployeeDTO employeeDTO = new EmployeeDTO(employee);
        
        return new Gson().toJson(employeeDTO);
    }

    @Path("highestpaid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getHighestPaid(){
        List<Employee> employees = facade.getEmployeesWithHighestSalary();
        List<EmployeeDTO> employeeDTOs = new ArrayList();
        EmployeeDTO employeeDTO;
        
        for (Employee employee : employees) {
            employeeDTO = new EmployeeDTO(employee);
            employeeDTOs.add(employeeDTO);
        }
        
        return new Gson().toJson(employeeDTOs);
    }
    
    @Path("name/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getByName(@PathParam("name") String name){
        List<Employee> employees = facade.getEmployeesByName(name);
        List<EmployeeDTO> employeeDTOs = new ArrayList();
        EmployeeDTO employeeDTO;
        
        for(Employee employee : employees){
            employeeDTO = new EmployeeDTO(employee);
            employeeDTOs.add(employeeDTO);
        }
        
        return new Gson().toJson(employeeDTOs);
    }
}
