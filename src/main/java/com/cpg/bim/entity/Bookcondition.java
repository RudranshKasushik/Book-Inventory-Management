package com.cpg.bim.entity;
 
import java.math.BigDecimal;
import java.util.List;
 
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany; // Import the OneToMany annotation
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="ranks")
 
@Entity
public class Bookcondition {
 
    @Id
    @Column(nullable = false)
    private int ranks;
 
    @Column(length = 50)
    private String description;
 
    @Column(length = 225)
    private String fulldescription;
 
    @Column(precision = 12, scale = 2)
    private BigDecimal price = new BigDecimal("30.00");
 
    // One-to-many relationship: A book condition can be associated with multiple inventory items
    @OneToMany(mappedBy = "bookcondition") // mappedBy indicates that Inventory is the owning side
    private List<Inventory> inventories;
 
    public Bookcondition() {}
 
    public Bookcondition(int ranks, String description, String fulldescription, BigDecimal price) {
        this.ranks = ranks;
        this.description = description;
        this.fulldescription = fulldescription;
        this.price = price;
    }
 
    // Getters and Setters
 
    public int getRanks() {
        return ranks;
    }
 
    public void setRanks(int ranks) {
        this.ranks = ranks;
    }
 
    public String getDescription() {
        return description;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
 
    public String getFulldescription() {
        return fulldescription;
    }
 
    public void setFulldescription(String fulldescription) {
        this.fulldescription = fulldescription;
    }
 
    public BigDecimal getPrice() {
        return price;
    }
 
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
 
    public List<Inventory> getInventories() {
        return inventories;
    }
 
    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }
}