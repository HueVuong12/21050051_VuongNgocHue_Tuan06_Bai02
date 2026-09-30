package vn.fit.vuongngochue_bai2_tuan06.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import vn.fit.vuongngochue_bai2_tuan06.dto.AddCartResponseDTO;
import vn.fit.vuongngochue_bai2_tuan06.dto.CartItemDetailDTO;
import vn.fit.vuongngochue_bai2_tuan06.dto.CheckoutSummaryDTO;
import vn.fit.vuongngochue_bai2_tuan06.dto.CustomerBillDTO;
import vn.fit.vuongngochue_bai2_tuan06.model.Product;
import vn.fit.vuongngochue_bai2_tuan06.model.ShoppingCart;
import vn.fit.vuongngochue_bai2_tuan06.repository.ProductRepository;
import vn.fit.vuongngochue_bai2_tuan06.repository.ShoppingCartRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ShoppingCartService {

    @Inject
    private ShoppingCartRepository cartRepository;

    @Inject
    private ProductRepository productRepository;

    public List<ShoppingCart> getAllCarts() {
        return cartRepository.findAll();
    }

    public ShoppingCart getCartById(int id) {
        return cartRepository.findById(id);
    }

    public boolean createCart(ShoppingCart cart) {
        return cartRepository.save(cart);
    }

    public boolean updateCart(ShoppingCart cart) {
        return cartRepository.update(cart);
    }

    public boolean deleteCart(int id) {
        return cartRepository.delete(id);
    }

    public CustomerBillDTO calculateCustomerBill(String customerName) {
        List<ShoppingCart> items = cartRepository.findByCustomerName(customerName);
        List<CartItemDetailDTO> itemDetails = new ArrayList<>();
        double subTotal = 0.0;

        for (ShoppingCart item : items) {
            Product p = productRepository.findById(item.getProductId());
            String prodName = (p != null) ? p.getName() : "Unknown Product";
            double price = (p != null) ? p.getPrice() : 0.0;
            double itemTotal = Math.round((item.getQuantity() * price) * 100.0) / 100.0;
            subTotal += itemTotal;

            itemDetails.add(new CartItemDetailDTO(
                    item.getId(),
                    prodName,
                    price,
                    item.getQuantity(),
                    itemTotal
            ));
        }

        subTotal = Math.round(subTotal * 100.0) / 100.0;
        double discount = 0.0;
        if (subTotal > 2000.0) {
            discount = Math.round((subTotal * 0.10) * 100.0) / 100.0;
        }
        double finalTotal = Math.round((subTotal - discount) * 100.0) / 100.0;

        return new CustomerBillDTO(customerName, itemDetails, subTotal, discount, finalTotal);
    }

    public AddCartResponseDTO addItemToCart(ShoppingCart cart) {
        Product product = productRepository.findById(cart.getProductId());
        if (product == null) {
            throw new IllegalArgumentException("Sản phẩm không tồn tại!");
        }

        double estimatedTotal = Math.round((product.getPrice() * cart.getQuantity()) * 100.0) / 100.0;
        if (estimatedTotal > 5000.0) {
            throw new IllegalStateException("Giá trị đơn hàng vượt quá hạn mức cho phép ($5,000)!");
        }

        cartRepository.save(cart);
        return new AddCartResponseDTO(cart.getId(), product.getName(), cart.getQuantity(), estimatedTotal);
    }

    /**
     * Yêu cầu 4: Checkout & Clear Cart
     * - Lấy danh sách item theo customerName
     * - Tính totalItems, totalQuantity, totalPaid
     * - Xóa toàn bộ item của khách hàng khỏi giỏ
     * - Trả về CheckoutSummaryDTO
     */
    public CheckoutSummaryDTO checkout(String customerName) {
        List<ShoppingCart> items = cartRepository.findByCustomerName(customerName);
        int totalItems = items.size();
        int totalQuantity = 0;
        double totalPaid = 0.0;

        for (ShoppingCart item : items) {
            totalQuantity += item.getQuantity();
            Product p = productRepository.findById(item.getProductId());
            if (p != null) {
                totalPaid += p.getPrice() * item.getQuantity();
            }
        }

        totalPaid = Math.round(totalPaid * 100.0) / 100.0;

        // Xóa sạch các record của customerName trong ShoppingCart
        cartRepository.deleteByCustomerName(customerName);

        return new CheckoutSummaryDTO(
                customerName,
                totalItems,
                totalQuantity,
                totalPaid,
                "CHECKOUT_COMPLETED_AND_CART_CLEARED"
        );
    }
}
