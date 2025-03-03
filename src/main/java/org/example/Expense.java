package org.example;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense {
    private int id;
    private String description;
    private double amount;
    private int categoryId;

    public Expense() {}
    public Expense(int id, String description, double amount, int categoryId) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.categoryId = categoryId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    @Override
    public String toString() {
        return "Expense: \nid = " + id +  "\ndescription = " + description + "\namount = " + amount + "\ncategoryId = " + categoryId;
    }
}
