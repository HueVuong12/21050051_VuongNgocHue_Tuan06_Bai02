package vn.fit.vuongngochue_bai2_tuan06;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import vn.fit.vuongngochue_bai2_tuan06.dto.*;
import vn.fit.vuongngochue_bai2_tuan06.model.Product;
import vn.fit.vuongngochue_bai2_tuan06.model.ShoppingCart;
import vn.fit.vuongngochue_bai2_tuan06.repository.ProductRepository;
import vn.fit.vuongngochue_bai2_tuan06.repository.ShoppingCartRepository;
import vn.fit.vuongngochue_bai2_tuan06.service.ProductService;
import vn.fit.vuongngochue_bai2_tuan06.service.ShoppingCartService;

import java.lang.reflect.Field;
import java.util.*;

public class ServiceLogicTest {

    static class MockProductRepository implements ProductRepository {
        Map<Integer, Product> map = new HashMap<>();

        public MockProductRepository() {
            map.put(1, new Product(1, "iPhone 15 Pro", 999.0, "Titanium", "img1"));
            map.put(2, new Product(2, "Samsung Galaxy S24", 850.0, "Galaxy AI", "img2"));
            map.put(3, new Product(3, "MacBook Air M2", 1199.0, "M2 Chip", "img3"));
            map.put(4, new Product(4, "Product Zero Demand", 100.0, "Test", "img4"));
        }

        @Override public List<Product> findAll() { return new ArrayList<>(map.values()); }
        @Override public Product findById(int id) { return map.get(id); }
        @Override public boolean save(Product product) { map.put(product.getId(), product); return true; }
        @Override public boolean update(Product product) { map.put(product.getId(), product); return true; }
        @Override public boolean delete(int id) { return map.remove(id) != null; }
    }

    static class MockShoppingCartRepository implements ShoppingCartRepository {
        List<ShoppingCart> list = new ArrayList<>();
        int nextId = 1;

        @Override public List<ShoppingCart> findAll() { return new ArrayList<>(list); }
        @Override public ShoppingCart findById(int id) {
            return list.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        }
        @Override public boolean save(ShoppingCart cart) {
            cart.setId(nextId++);
            list.add(cart);
            return true;
        }
        @Override public boolean update(ShoppingCart cart) { return true; }
        @Override public boolean delete(int id) { return list.removeIf(c -> c.getId() == id); }
        @Override public List<ShoppingCart> findByCustomerName(String customerName) {
            List<ShoppingCart> res = new ArrayList<>();
            for (ShoppingCart c : list) {
                if (c.getCustomerName().equalsIgnoreCase(customerName)) res.add(c);
            }
            return res;
        }
        @Override public boolean deleteByCustomerName(String customerName) {
            return list.removeIf(c -> c.getCustomerName().equalsIgnoreCase(customerName));
        }
    }

    private void inject(Object target, String fieldName, Object val) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, val);
    }

    @Test
    public void testRequirement1_CustomerBillBreakdown() throws Exception {
        ShoppingCartService cartService = new ShoppingCartService();
        MockProductRepository prodRepo = new MockProductRepository();
        MockShoppingCartRepository cartRepo = new MockShoppingCartRepository();

        inject(cartService, "productRepository", prodRepo);
        inject(cartService, "cartRepository", cartRepo);

        // Nguyen Van A buys iPhone 15 Pro (999.0 * 1 = 999.0) -> subTotal <= 2000 -> discount 0
        cartRepo.save(new ShoppingCart(1, 1, "Nguyen Van A", 1, null));
        CustomerBillDTO billA = cartService.calculateCustomerBill("Nguyen Van A");
        Assertions.assertEquals(999.0, billA.getSubTotal());
        Assertions.assertEquals(0.0, billA.getDiscount());
        Assertions.assertEquals(999.0, billA.getFinalTotal());

        // Tran Thi B buys MacBook Air M2 (1199.0 * 2 = 2398.0) -> subTotal > 2000 -> discount 10% = 239.8
        cartRepo.save(new ShoppingCart(2, 3, "Tran Thi B", 2, null));
        CustomerBillDTO billB = cartService.calculateCustomerBill("Tran Thi B");
        Assertions.assertEquals(2398.0, billB.getSubTotal());
        Assertions.assertEquals(239.8, billB.getDiscount());
        Assertions.assertEquals(2158.2, billB.getFinalTotal());
    }

    @Test
    public void testRequirement2_AddWithCap() throws Exception {
        ShoppingCartService cartService = new ShoppingCartService();
        MockProductRepository prodRepo = new MockProductRepository();
        MockShoppingCartRepository cartRepo = new MockShoppingCartRepository();

        inject(cartService, "productRepository", prodRepo);
        inject(cartService, "cartRepository", cartRepo);

        // Valid cap: MacBook Air M2 (1199 * 2 = 2398 <= 5000)
        ShoppingCart validCart = new ShoppingCart(0, 3, "Sinh Vien Test", 2, null);
        AddCartResponseDTO res = cartService.addItemToCart(validCart);
        Assertions.assertEquals(2398.0, res.getEstimatedTotal());

        // Over cap: MacBook Air M2 (1199 * 10 = 11990 > 5000) -> should throw IllegalStateException
        ShoppingCart overCapCart = new ShoppingCart(0, 3, "Sinh Vien Test", 10, null);
        Assertions.assertThrows(IllegalStateException.class, () -> {
            cartService.addItemToCart(overCapCart);
        });

        // Non-existent product -> should throw IllegalArgumentException
        ShoppingCart nonExistent = new ShoppingCart(0, 999, "Sinh Vien Test", 1, null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            cartService.addItemToCart(nonExistent);
        });
    }

    @Test
    public void testRequirement3_DynamicRepricing() throws Exception {
        ProductService productService = new ProductService();
        MockProductRepository prodRepo = new MockProductRepository();
        MockShoppingCartRepository cartRepo = new MockShoppingCartRepository();

        inject(productService, "productRepository", prodRepo);
        inject(productService, "cartRepository", cartRepo);

        // Add 6 units of Product 2 (Samsung S24, oldPrice 850) -> totalQty >= 5 -> +10%
        cartRepo.save(new ShoppingCart(0, 2, "Cust1", 3, null));
        cartRepo.save(new ShoppingCart(0, 2, "Cust2", 3, null));

        // Product 4 has 0 units in cart -> -5% (oldPrice 100 -> newPrice 95)

        List<RepriceReportDTO> reports = productService.applyDynamicRepricing();
        Assertions.assertFalse(reports.isEmpty());

        RepriceReportDTO p2Report = reports.stream().filter(r -> r.getProductId() == 2).findFirst().orElse(null);
        Assertions.assertNotNull(p2Report);
        Assertions.assertEquals(935.0, p2Report.getNewPrice());
        Assertions.assertEquals("+10%", p2Report.getAdjustment());

        RepriceReportDTO p4Report = reports.stream().filter(r -> r.getProductId() == 4).findFirst().orElse(null);
        Assertions.assertNotNull(p4Report);
        Assertions.assertEquals(95.0, p4Report.getNewPrice());
        Assertions.assertEquals("-5%", p4Report.getAdjustment());
    }

    @Test
    public void testRequirement4_CheckoutAndClearCart() throws Exception {
        ShoppingCartService cartService = new ShoppingCartService();
        MockProductRepository prodRepo = new MockProductRepository();
        MockShoppingCartRepository cartRepo = new MockShoppingCartRepository();

        inject(cartService, "productRepository", prodRepo);
        inject(cartService, "cartRepository", cartRepo);

        cartRepo.save(new ShoppingCart(0, 3, "Le Van C", 1, null));
        CheckoutSummaryDTO summary = cartService.checkout("Le Van C");

        Assertions.assertEquals("Le Van C", summary.getCustomerName());
        Assertions.assertEquals(1, summary.getTotalItems());
        Assertions.assertEquals(1, summary.getTotalQuantity());
        Assertions.assertEquals(1199.0, summary.getTotalPaid());
        Assertions.assertEquals("CHECKOUT_COMPLETED_AND_CART_CLEARED", summary.getStatus());

        // Verify cart is cleared
        Assertions.assertTrue(cartRepo.findByCustomerName("Le Van C").isEmpty());
    }

    @Test
    public void testRequirement5_RevenueShareAnalytics() throws Exception {
        ProductService productService = new ProductService();
        MockProductRepository prodRepo = new MockProductRepository();
        MockShoppingCartRepository cartRepo = new MockShoppingCartRepository();

        inject(productService, "productRepository", prodRepo);
        inject(productService, "cartRepository", cartRepo);

        // Product 1: 3 units * 999 = 2997
        cartRepo.save(new ShoppingCart(0, 1, "A", 3, null));
        // Product 3: 2 units * 1199 = 2398
        cartRepo.save(new ShoppingCart(0, 3, "B", 2, null));
        // Total = 5395. P1 share = 2997/5395 * 100 = 55.55%, P3 share = 44.45%

        List<RevenueShareDTO> shares = productService.getRevenueShareReport();
        Assertions.assertEquals(2, shares.size());
        // Should be sorted descending by revenue
        Assertions.assertEquals(1, shares.get(0).getProductId());
        Assertions.assertEquals(2997.0, shares.get(0).getRevenueContribution());
        Assertions.assertEquals(3, shares.get(1).getProductId());
        Assertions.assertEquals(2398.0, shares.get(1).getRevenueContribution());
    }
}
