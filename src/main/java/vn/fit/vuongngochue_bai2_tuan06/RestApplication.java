package vn.fit.vuongngochue_bai2_tuan06;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import vn.fit.vuongngochue_bai2_tuan06.repository.ProductRepository;
import vn.fit.vuongngochue_bai2_tuan06.repository.ShoppingCartRepository;
import vn.fit.vuongngochue_bai2_tuan06.repository.impl.ProductRepositoryImpl;
import vn.fit.vuongngochue_bai2_tuan06.repository.impl.ShoppingCartRepositoryImpl;
import vn.fit.vuongngochue_bai2_tuan06.service.ProductService;
import vn.fit.vuongngochue_bai2_tuan06.service.ShoppingCartService;

@ApplicationPath("/api")
public class RestApplication extends ResourceConfig {
    public RestApplication() {
        packages("vn.fit.vuongngochue_bai2_tuan06.controller");
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindAsContract(ProductRepositoryImpl.class).to(ProductRepository.class);
                bindAsContract(ShoppingCartRepositoryImpl.class).to(ShoppingCartRepository.class);
                bindAsContract(ProductService.class);
                bindAsContract(ShoppingCartService.class);
            }
        });
    }
}
