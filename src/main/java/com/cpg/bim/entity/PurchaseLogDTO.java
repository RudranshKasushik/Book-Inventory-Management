package com.cpg.bim.entity;
public class PurchaseLogDTO
{
	private int userId;
    private int inventoryId;
    private int newInventory;
 
    // Default constructor (needed for Jackson deserialization)
    public PurchaseLogDTO() {
    }
 
    // Constructor with all fields
    public PurchaseLogDTO(int userId, int inventoryId, int newInventory) {
        this.userId = userId;
        this.inventoryId = inventoryId;
        this.newInventory = newInventory;
    }
 
    // Constructor with only userId and inventoryId
    public PurchaseLogDTO(int userId, int inventoryId) {
        this.userId = userId;
        this.inventoryId = inventoryId;
    }
 
    // Getters and Setters
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
 
    public int getNewInventory() {
        return newInventory;
    }
 
    public void setNewInventory(int newInventory) {
        this.newInventory = newInventory;
    }
}
 