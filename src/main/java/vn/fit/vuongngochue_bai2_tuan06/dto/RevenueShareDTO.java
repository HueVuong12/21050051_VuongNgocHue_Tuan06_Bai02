package vn.fit.vuongngochue_bai2_tuan06.dto;

public class RevenueShareDTO {
    private int productId;
    private String productName;
    private int totalUnitsInCart;
    private double revenueContribution;
    private String percentage;

    public RevenueShareDTO() {
    }

    public RevenueShareDTO(int productId, String productName, int totalUnitsInCart, double revenueContribution, String percentage) {
        this.productId = productId;
        this.productName = productName;
        this.totalUnitsInCart = totalUnitsInCart;
        this.revenueContribution = revenueContribution;
        this.percentage = percentage;
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

    public int getTotalUnitsInCart() {
        return totalUnitsInCart;
    }

    public void setTotalUnitsInCart(int totalUnitsInCart) {
        this.totalUnitsInCart = totalUnitsInCart;
    }

    public double getRevenueContribution() {
        return revenueContribution;
    }

    public void setRevenueContribution(double revenueContribution) {
        this.revenueContribution = revenueContribution;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
