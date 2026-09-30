package vn.fit.vuongngochue_bai2_tuan06.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import vn.fit.vuongngochue_bai2_tuan06.model.Account;
import vn.fit.vuongngochue_bai2_tuan06.repository.AccountRepository;
import vn.fit.vuongngochue_bai2_tuan06.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AccountRepositoryImpl implements AccountRepository {

    private static final List<Account> memoryStore = new ArrayList<>();
    private static int memoryId = 1;

    @Override
    public boolean save(Account account) {
        String sql = "INSERT INTO accounts (firstname, lastname, email, password, dateofbirth) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, account.getFirstname());
            ps.setString(2, account.getLastname());
            ps.setString(3, account.getEmail());
            ps.setString(4, account.getPassword());
            ps.setDate(5, account.getDateOfBirth());
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        account.setId(rs.getInt(1));
                    }
                }
                memoryStore.add(account);
                return true;
            }
        } catch (Exception e) {
            System.err.println("DB Connection Warning (save account): " + e.getMessage());
            account.setId(memoryId++);
            memoryStore.add(account);
            return true;
        }
        return false;
    }

    @Override
    public List<Account> findAll() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT id, firstname, lastname, email, dateofbirth FROM accounts ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Account(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        null,
                        rs.getDate("dateofbirth")));
            }
            return list;
        } catch (Exception e) {
            System.err.println("DB Connection Warning (findAll accounts): " + e.getMessage());
            return new ArrayList<>(memoryStore);
        }
    }
}
