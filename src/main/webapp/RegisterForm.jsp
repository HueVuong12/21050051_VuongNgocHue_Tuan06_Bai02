<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.fit.vuongngochue_bai2_tuan06.model.Account" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Registration Form</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
        }

        body {
            background-color: #f8fafc;
            padding: 30px 15px;
            color: #1e293b;
        }

        .main-wrapper {
            max-width: 850px;
            margin: 0 auto;
        }

        /* Khung xanh bao quanh form đăng ký giống hệt ảnh mẫu */
        .registration-card {
            border: 2px solid #5aa43e;
            border-radius: 4px;
            padding: 30px 25px;
            background: #ffffff;
            max-width: 520px;
            margin: 0 auto 30px auto;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05);
        }

        .form-title {
            text-align: center;
            font-size: 26px;
            font-weight: 700;
            color: #1e293b;
            margin-bottom: 22px;
        }

        .name-row {
            display: flex;
            gap: 12px;
            margin-bottom: 14px;
        }

        .name-row .input-group {
            flex: 1;
        }

        .form-control {
            width: 100%;
            padding: 10px 14px;
            border: 1px solid #d1d5db;
            border-radius: 6px;
            font-size: 14px;
            color: #374151;
            outline: none;
            transition: border-color 0.2s;
            margin-bottom: 14px;
            background-color: #ffffff;
        }

        .form-control:focus {
            border-color: #3b82f6;
        }

        .section-label {
            font-size: 14px;
            font-weight: 600;
            color: #334155;
            margin-bottom: 6px;
            display: block;
        }

        .birthday-row {
            display: flex;
            gap: 10px;
            margin-bottom: 16px;
        }

        .birthday-select {
            flex: 1;
            padding: 10px 12px;
            border: 1px solid #d1d5db;
            border-radius: 6px;
            font-size: 14px;
            color: #374151;
            background-color: #ffffff;
            outline: none;
            cursor: pointer;
        }

        .gender-row {
            display: flex;
            align-items: center;
            gap: 20px;
            margin-bottom: 22px;
            font-size: 14px;
            color: #374151;
        }

        .gender-option {
            display: flex;
            align-items: center;
            gap: 6px;
            cursor: pointer;
        }

        .btn-signup {
            width: 100%;
            background-color: #0066ff;
            color: #ffffff;
            border: none;
            border-radius: 8px;
            padding: 13px;
            font-size: 17px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.2s;
        }

        .btn-signup:hover {
            background-color: #0052cc;
        }

        .alert-msg {
            padding: 10px 15px;
            border-radius: 6px;
            margin-bottom: 15px;
            font-size: 14px;
            text-align: center;
        }

        .alert-success {
            background-color: #dcfce7;
            color: #166534;
            border: 1px solid #bbf7d0;
        }

        .alert-danger {
            background-color: #fee2e2;
            color: #991b1b;
            border: 1px solid #fecaca;
        }

    </style>
</head>
<body>

<div class="main-wrapper">

    <!-- Form đăng ký viền xanh chuẩn như ảnh mẫu -->
    <div class="registration-card">
        <h1 class="form-title">User Registration Form</h1>

        <% if (request.getAttribute("message") != null) { %>
            <div class="alert-msg alert-success">
                <%= request.getAttribute("message") %>
            </div>
        <% } %>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert-msg alert-danger">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <form action="<%= request.getContextPath() %>/register" method="POST">
            <!-- First Name & Last Name (2 cột) -->
            <div class="name-row">
                <div class="input-group">
                    <input type="text" name="firstName" class="form-control" placeholder="First Name" required />
                </div>
                <div class="input-group">
                    <input type="text" name="lastName" class="form-control" placeholder="Last Name" required />
                </div>
            </div>

            <!-- Your Email -->
            <input type="email" name="email" class="form-control" placeholder="Your Email" required />

            <!-- Password -->
            <input type="password" name="password" class="form-control" placeholder="Password" required />

            <!-- Birthday -->
            <label class="section-label">Birthday</label>
            <div class="birthday-row">
                <select name="month" class="birthday-select" required>
                    <option value="" disabled selected>Month</option>
                    <option value="1">January</option>
                    <option value="2">February</option>
                    <option value="3">March</option>
                    <option value="4">April</option>
                    <option value="5">May</option>
                    <option value="6">June</option>
                    <option value="7">July</option>
                    <option value="8">August</option>
                    <option value="9">September</option>
                    <option value="10">October</option>
                    <option value="11">November</option>
                    <option value="12">December</option>
                </select>

                <select name="day" class="birthday-select" required>
                    <option value="" disabled selected>Day</option>
                    <% for (int d = 1; d <= 31; d++) { %>
                        <option value="<%= d %>"><%= d %></option>
                    <% } %>
                </select>

                <select name="year" class="birthday-select" required>
                    <option value="" disabled selected>Year</option>
                    <% for (int y = 2026; y >= 1950; y--) { %>
                        <option value="<%= y %>"><%= y %></option>
                    <% } %>
                </select>
            </div>

            <!-- Gender -->
            <label class="section-label">Gender</label>
            <div class="gender-row">
                <label class="gender-option">
                    <input type="radio" name="gender" value="Female" /> Female
                </label>
                <label class="gender-option">
                    <input type="radio" name="gender" value="Male" checked /> Male
                </label>
            </div>

            <!-- Sign Up Button -->
            <button type="submit" class="btn-signup">Sign Up</button>
        </form>
    </div>

</div>

</body>
</html>
