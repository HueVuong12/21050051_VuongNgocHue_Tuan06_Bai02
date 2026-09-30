package vn.fit.vuongngochue_bai2_tuan06.dto;

import java.util.List;

public class CustomerBillDTO {
    private String customerName;
    private List<CartItemDetailDTO> items;
    private double subTotal;
    private double discount;
    private double finalTotal;

    public CustomerBillDTO() {
    }

    public CustomerBillDTO(String customerName, List<CartItemDetailDTO> items, double subTotal, double discount, double finalTotal) {
        this.customerName = customerName;
        this.items = items;
        this.subTotal = subTotal;
        this.discount = discount;
        this.finalTotal = finalTotal;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<CartItemDetailDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDetailDTO> items) {
        this.items = items;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(double finalTotal) {
        this.finalTotal = finalTotal;
    }
}
