package vn.fit.vuongngochue_bai2_tuan06.repository;

import vn.fit.vuongngochue_bai2_tuan06.model.ShoppingCart;
import java.util.List;

public interface ShoppingCartRepository {
    List<ShoppingCart> findAll();
    ShoppingCart findById(int id);
    boolean save(ShoppingCart cart);
    boolean update(ShoppingCart cart);
    boolean delete(int id);
    List<ShoppingCart> findByCustomerName(String customerName);
    boolean deleteByCustomerName(String customerName);
}
