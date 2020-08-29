package dto;

import entities.BankCustomer;
import java.util.Objects;

/**
 *
 * @author Nicklas Nielsen
 */
public class CustomerDTO {
    
    private int customerID;
    private String fullName;
    private String accountNumber;
    private double balance;
    
    public CustomerDTO(BankCustomer bankCustomer){
        customerID = bankCustomer.getId();
        fullName = bankCustomer.getLastName() + ", " + bankCustomer.getFirstName();
        accountNumber = bankCustomer.getAccountNumber();
        balance = bankCustomer.getBalance();
    }
    
    public CustomerDTO(){
        
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CustomerDTO other = (CustomerDTO) obj;
        if (Double.doubleToLongBits(this.balance) != Double.doubleToLongBits(other.balance)) {
            return false;
        }
        if (!Objects.equals(this.fullName, other.fullName)) {
            return false;
        }
        if (!Objects.equals(this.accountNumber, other.accountNumber)) {
            return false;
        }
        return true;
    }
}
