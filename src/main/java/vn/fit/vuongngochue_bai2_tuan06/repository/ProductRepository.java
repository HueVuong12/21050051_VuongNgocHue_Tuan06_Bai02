package vn.fit.vuongngochue_bai2_tuan06.repository;

import vn.fit.vuongngochue_bai2_tuan06.model.Product;
import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    Product findById(int id);
    boolean save(Product product);
    boolean update(Product product);
    boolean delete(int id);
}
