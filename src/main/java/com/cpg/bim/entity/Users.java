package com.cpg.bim.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property = "userid")
public class Users {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate userid
    @Column(name="userid")
    private int userid;

    @Column(name="lastname")
    private String lastname;

    @Column(name="firstname")
    private String firstname;

    @Column(name="phonenumber")
    private String phonenumber;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "rolenumber", nullable=false)
    private PermRole permrole;

    @OneToMany(mappedBy = "users")
    private List<PurchaseLog> purchaseLogs;

    // Default constructor
    public Users() {}

    // Parameterized constructor
    public Users(String lastname, String firstname, String phonenumber, String username, String password, PermRole permrole) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.phonenumber = phonenumber;
        this.username = username;
        this.password = password;
        this.permrole = permrole;
    }

    // Getter and Setter methods
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PermRole getPermrole() {
        return permrole;
    }

    public void setPermrole(PermRole permrole) {
        this.permrole = permrole;
    }

    public List<PurchaseLog> getPurchaseLogs() {
        return purchaseLogs;
    }

    public void setPurchaseLogs(List<PurchaseLog> purchaseLogs) {
        this.purchaseLogs = purchaseLogs;
    }
}
