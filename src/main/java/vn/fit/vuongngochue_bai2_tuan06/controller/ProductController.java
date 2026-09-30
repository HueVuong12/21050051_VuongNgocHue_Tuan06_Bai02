package vn.fit.vuongngochue_bai2_tuan06.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.fit.vuongngochue_bai2_tuan06.dto.RepriceReportDTO;
import vn.fit.vuongngochue_bai2_tuan06.dto.RevenueShareDTO;
import vn.fit.vuongngochue_bai2_tuan06.model.Product;
import vn.fit.vuongngochue_bai2_tuan06.service.ProductService;

import java.util.List;

@RequestScoped
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {

    @Inject
    private ProductService productService;

    @GET
    public Response getAll() {
        List<Product> products = productService.getAllProducts();
        return Response.ok(products).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id) {
        Product p = productService.getProductById(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Product not found\"}")
                    .build();
        }
        return Response.ok(p).build();
    }

    @POST
    public Response create(Product product) {
        boolean success = productService.createProduct(product);
        if (success) {
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Product created\"}")
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"message\": \"Failed to create product\"}")
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Product product) {
        product.setId(id);
        boolean success = productService.updateProduct(product);
        if (success) {
            return Response.ok("{\"message\": \"Product updated\"}").build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        boolean success = productService.deleteProduct(id);
        if (success) {
            return Response.ok("{\"message\": \"Product deleted\"}").build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/dynamic-pricing")
    public Response dynamicPricing() {
        List<RepriceReportDTO> report = productService.applyDynamicRepricing();
        return Response.ok(report).build();
    }

    @GET
    @Path("/analytics/revenue-share")
    public Response getRevenueShare() {
        List<RevenueShareDTO> stats = productService.getRevenueShareReport();
        return Response.ok(stats).build();
    }
}
