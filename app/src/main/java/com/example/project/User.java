package com.example.project;

public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;

    public User(){ }
    public User(String userId, String firstName, String lastName, String phoneNumber, String emailAddress){
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId(){ return userId; }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getFirstName(){ return firstName; }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getLastName(){ return lastName; }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber(){ return phoneNumber; }

    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }
    public String getEmailAddress(){ return emailAddress; }

    public String toString(){
        return "\n"+ userId +" - " + firstName + " " + lastName + "\n\t\t\tPhone Number: " + phoneNumber + "\n\t\t\tEmail: " + emailAddress + "\n";
    }
}