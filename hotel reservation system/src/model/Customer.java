package model;

import java.util.regex.Pattern;

public class Customer {
    private String firstName, lastName, email;
    private final String emailRegex = "^(.+)@(.+).(.+)$";
    private final Pattern pattern = Pattern.compile(emailRegex);

    public Customer(String firstName, String lastName, String email) {
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Wrong email format!");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {return firstName;}

    public String getLastName() {return lastName;}

    public String getEmail() {
        if (pattern.matcher(email).matches()) {return email;}
        return null;
    }

    @Override
    public String toString() {
        return "Customer's first name: " + firstName + " Customer's last name: " + lastName +
                " Customer's email: " + email;
    }

}
