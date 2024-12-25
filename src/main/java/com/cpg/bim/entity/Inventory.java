package com.cpg.bim.entity;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
 
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
 
import jakarta.persistence.CascadeType;  // Importing CascadeType
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="inventoryid")
 
@Entity
public class Inventory {
 
    @Id
    private int inventoryid;
 
    // Add CascadeType.ALL to persist Book and BookCondition automatically when Inventory is persisted
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "isbn", referencedColumnName = "isbn", nullable = false)
    private Book book;
 
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ranks", referencedColumnName = "ranks", nullable = false)
    private Bookcondition bookcondition;
 
    @Column(nullable = false)
    private boolean purchased = false;
 
    
    public Inventory() {}
 
   
    public Inventory(int inventoryid, Book book, Bookcondition bookcondition, boolean purchased) {
        this.inventoryid = inventoryid;
        this.book = book;
        this.bookcondition = bookcondition;
        this.purchased = purchased;
    }
 
   
    public int getInventoryid() {
        return inventoryid;
    }
 
    public void setInventoryid(int inventoryid) {
        this.inventoryid = inventoryid;
    }
 
    public Book getBook() {
        return book;
    }
 
    public void setBook(Book book) {
        this.book = book;
    }
 
    public Bookcondition getBookcondition() {
        return bookcondition;
    }
 
    public void setBookcondition(Bookcondition bookcondition) {
        this.bookcondition = bookcondition;
    }
 
    public boolean isPurchased() {
        return purchased;
    }
 
    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}