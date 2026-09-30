package vn.fit.vuongngochue_bai2_tuan06.dto;

public class CheckoutSummaryDTO {
    private String customerName;
    private int totalItems;
    private int totalQuantity;
    private double totalPaid;
    private String status;

    public CheckoutSummaryDTO() {
    }

    public CheckoutSummaryDTO(String customerName, int totalItems, int totalQuantity, double totalPaid, String status) {
        this.customerName = customerName;
        this.totalItems = totalItems;
        this.totalQuantity = totalQuantity;
        this.totalPaid = totalPaid;
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
