package HostelManagementSystem;

import java.util.Scanner;

abstract class Users implements Manageable
{
    protected String userID;
    protected String name;
    protected String email;
    protected String phone;
    protected String password;

    public Users(String userID, String name, String email, String phoneNumber, String password) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.phone  = phoneNumber;
        this.password = password;
    }
 // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }

 // Getter for name
    public String getName() {
        return name;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Getter for phone
    public String getPhone() {
        return phone;
    }
    
    public String getUserID() 
    {
        return userID;
    }

    public boolean login(String enteredPassword) 
    {
        return this.password.equals(enteredPassword);
    }
    
    //abstract methods
    public abstract void viewAccountDetails();
    public abstract void editAccountDetails(Scanner scanner);
}
