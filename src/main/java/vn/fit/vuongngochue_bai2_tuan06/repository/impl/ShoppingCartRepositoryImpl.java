package vn.fit.vuongngochue_bai2_tuan06.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import vn.fit.vuongngochue_bai2_tuan06.model.ShoppingCart;
import vn.fit.vuongngochue_bai2_tuan06.repository.ShoppingCartRepository;
import vn.fit.vuongngochue_bai2_tuan06.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ShoppingCartRepositoryImpl implements ShoppingCartRepository {

    @Override
    public List<ShoppingCart> findAll() {
        List<ShoppingCart> list = new ArrayList<>();
        String sql = "SELECT * FROM ShoppingCart";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ShoppingCart findById(int id) {
        String sql = "SELECT * FROM ShoppingCart WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(ShoppingCart cart) {
        String sql = "INSERT INTO ShoppingCart (product_id, customer_name, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, cart.getProductId());
            ps.setString(2, cart.getCustomerName());
            ps.setInt(3, cart.getQuantity());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cart.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(ShoppingCart cart) {
        String sql = "UPDATE ShoppingCart SET product_id = ?, customer_name = ?, quantity = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cart.getProductId());
            ps.setString(2, cart.getCustomerName());
            ps.setInt(3, cart.getQuantity());
            ps.setInt(4, cart.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM ShoppingCart WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ShoppingCart> findByCustomerName(String customerName) {
        List<ShoppingCart> list = new ArrayList<>();
        String sql = "SELECT * FROM ShoppingCart WHERE customer_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customerName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteByCustomerName(String customerName) {
        String sql = "DELETE FROM ShoppingCart WHERE customer_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customerName);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ShoppingCart mapResultSet(ResultSet rs) throws SQLException {
        return new ShoppingCart(
                rs.getInt("id"),
                rs.getInt("product_id"),
                rs.getString("customer_name"),
                rs.getInt("quantity"),
                rs.getTimestamp("created_at")
        );
    }
}
