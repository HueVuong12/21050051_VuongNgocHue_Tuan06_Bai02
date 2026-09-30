package vn.fit.vuongngochue_bai2_tuan06.dto;

public class RepriceReportDTO {
    private int productId;
    private String productName;
    private int totalQuantityOrdered;
    private double oldPrice;
    private double newPrice;
    private String adjustment;

    public RepriceReportDTO() {
    }

    public RepriceReportDTO(int productId, String productName, int totalQuantityOrdered, double oldPrice, double newPrice, String adjustment) {
        this.productId = productId;
        this.productName = productName;
        this.totalQuantityOrdered = totalQuantityOrdered;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.adjustment = adjustment;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getTotalQuantityOrdered() {
        return totalQuantityOrdered;
    }

    public void setTotalQuantityOrdered(int totalQuantityOrdered) {
        this.totalQuantityOrdered = totalQuantityOrdered;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public String getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(String adjustment) {
        this.adjustment = adjustment;
    }
}
