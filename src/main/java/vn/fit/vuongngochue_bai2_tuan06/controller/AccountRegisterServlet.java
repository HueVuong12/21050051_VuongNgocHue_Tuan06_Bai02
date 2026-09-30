package vn.fit.vuongngochue_bai2_tuan06.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.fit.vuongngochue_bai2_tuan06.model.Account;
import vn.fit.vuongngochue_bai2_tuan06.repository.AccountRepository;
import vn.fit.vuongngochue_bai2_tuan06.repository.impl.AccountRepositoryImpl;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "AccountRegisterServlet", urlPatterns = { "/register", "/account" })
public class AccountRegisterServlet extends HttpServlet {

    private final AccountRepository accountRepo = new AccountRepositoryImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        loadAccountsAndForward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String month = req.getParameter("month");
        String day = req.getParameter("day");
        String year = req.getParameter("year");

        Date dob = null;
        try {
            if (year != null && month != null && day != null &&
                    !year.isEmpty() && !month.isEmpty() && !day.isEmpty()) {
                int m = Integer.parseInt(month);
                int d = Integer.parseInt(day);
                int y = Integer.parseInt(year);
                String dateStr = String.format("%04d-%02d-%02d", y, m, d);
                dob = Date.valueOf(dateStr);
            }
        } catch (Exception e) {
            System.err.println("Invalid date: " + e.getMessage());
        }

        if (firstName != null && !firstName.trim().isEmpty() &&
                email != null && !email.trim().isEmpty()) {
            Account account = new Account(firstName, lastName, email, password, dob);
            boolean saved = accountRepo.save(account);
            if (saved) {
                req.setAttribute("message", "Đăng ký tài khoản thành công!");
            } else {
                req.setAttribute("error", "Lỗi: Không thể lưu tài khoản vào cơ sở dữ liệu.");
            }
        } else {
            req.setAttribute("error", "Vui lòng nhập đầy đủ thông tin bắt buộc!");
        }

        loadAccountsAndForward(req, resp);
    }

    private void loadAccountsAndForward(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Account> accounts = accountRepo.findAll();
        req.setAttribute("accounts", accounts);
        req.getRequestDispatcher("/RegisterForm.jsp").forward(req, resp);
    }
}
