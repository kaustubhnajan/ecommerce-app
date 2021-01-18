package com.ecommerce.dto;

import com.ecommerce.model.User;

/**
 * Represents the User DTO
 */
public class UserDTO {

    /** User id of the user */
    private String id;

    /** User's email id */
    private String emailId;

    /** User's full name */
    private String fname;

    /** User's last name */
    private String lname;

    /** User's mobile */
    private String mobile;

    /** User's address */
    private String address;

    /** Type of user */
    private User.UserType type;

    private UserDTO() { }

    public UserDTO(String id, String emailId, String fname, String lname, String mobile,
                   String address, User.UserType type) {
        this.id = id;
        this.emailId = emailId;
        this.fname = fname;
        this.lname = lname;
        this.mobile = mobile;
        this.address = address;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public User.UserType getType() {
        return type;
    }
}
