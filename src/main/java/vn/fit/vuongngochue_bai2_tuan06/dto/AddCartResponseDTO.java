package vn.fit.vuongngochue_bai2_tuan06.dto;

public class AddCartResponseDTO {
    private int cartId;
    private String productName;
    private int quantity;
    private double estimatedTotal;

    public AddCartResponseDTO() {
    }

    public AddCartResponseDTO(int cartId, String productName, int quantity, double estimatedTotal) {
        this.cartId = cartId;
        this.productName = productName;
        this.quantity = quantity;
        this.estimatedTotal = estimatedTotal;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getEstimatedTotal() {
        return estimatedTotal;
    }

    public void setEstimatedTotal(double estimatedTotal) {
        this.estimatedTotal = estimatedTotal;
    }
}
