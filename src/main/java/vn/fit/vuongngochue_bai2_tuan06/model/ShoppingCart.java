package vn.fit.vuongngochue_bai2_tuan06.model;

import java.sql.Timestamp;

public class ShoppingCart {
    private int id;
    private int productId;
    private String customerName;
    private int quantity;
    private Timestamp createdAt;

    public ShoppingCart() {
    }

    public ShoppingCart(int id, int productId, String customerName, int quantity, Timestamp createdAt) {
        this.id = id;
        this.productId = productId;
        this.customerName = customerName;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
