package dto;

import entities.Employee;

/**
 * 
 * @author Nicklas Nielsen
 */
public class EmployeeDTO {

    private int id;
    private String name;
    private String address;

    public EmployeeDTO(Employee employee) {
        id = employee.getId();
        name = employee.getName();
        address = employee.getAddress();
    }
    
    public EmployeeDTO(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
