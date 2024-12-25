package com.cpg.bim.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PurchaseLogID implements Serializable 
{
	private int userId;
    private int inventoryId;
    // Default constructor, getters, setters, equals, and hashCode methods
    public PurchaseLogID() {}
    public PurchaseLogID(int userId, int inventoryId) {
        this.userId = userId;
        this.inventoryId = inventoryId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getInventoryId() {
        return inventoryId;
    }
    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }
    // Override equals and hashCode based on userId and inventoryId
    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseLogID that = (PurchaseLogID) o;
        // Use primitive comparison with '=='
        return userId == that.userId && inventoryId == that.inventoryId;
    }
    @Override
    public int hashCode() 
    {
        // Use primitive comparison with '=='
        int result = Integer.hashCode(userId);  // Using Integer.hashCode() for primitive int
        result = 31 * result + Integer.hashCode(inventoryId);  // Combine both fields
        return result;
    }
}