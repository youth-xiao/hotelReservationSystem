package service;

import model.Customer;

import java.util.Collection;
import java.util.HashSet;

public class CustomerService { // *** Passed Driver Test!
    /*
    Static reference: an 'unbound' reference to an attribute, method, or member class.
    Static method: there is just one copy of that method - can call that method w/o having
                   an instance of that class
    Instance control: Singleton prevents other objects from instantiating their own copies
    of the Singleton object, ensuring all objects access the single instance.
     */

    /*
    Sources:
    https://knowledge.udacity.com/questions/544862
     */

    private static CustomerService customerService;
    Collection<Customer> customers = new HashSet<>();

    private CustomerService() {}

    public static CustomerService getInstance() {
        if (customerService == null) {customerService = new CustomerService();}
        return customerService;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        if (!customers.add(customer)) {System.out.print("The customer info already exists in the system!");}
        else {
            System.out.println("Customer: " + firstName + " " + lastName + " with the email: " + email
                    + " has been successfully added to the system!");}
        }

    public Customer getCustomer(String customerEmail) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(customerEmail)) {
                System.out.print(customer);
                return customer;
            }
        }
        System.out.println("Sorry, there is no such a customer associated with the email provided.");
        return null;
    }

    public Collection<Customer> getAllCustomers() {
        for (Customer customer : customers) {
            System.out.println(customer);
        }
        return customers;
    }
}
