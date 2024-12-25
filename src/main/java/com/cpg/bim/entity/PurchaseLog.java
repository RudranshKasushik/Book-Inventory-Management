package com.cpg.bim.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Table(name="purchaselog")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"user"}) // Ignore the 'user' field when serializing the PurchaseLog
public class PurchaseLog 
{
	@EmbeddedId
    private PurchaseLogID id;  // Composite ID using PurchaselogID class
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userid")
    @JsonIgnore
    private Users users;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("inventoryId")
    @JoinColumn(name = "inventoryid", nullable=false)
    @JsonIgnore  
    private Inventory inventory;
    // Getters and setters
    public PurchaseLogID getId() {
        return id;
    }
    public void setId(PurchaseLogID id) {
        this.id = id;
    }
    public Users getUser() {
        return users;
    }
    public void setUser(Users users) {
        this.users = users;
    }
    public Inventory getInventory() {
        return inventory;
    }
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}