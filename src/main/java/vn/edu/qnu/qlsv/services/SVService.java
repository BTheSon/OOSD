package vn.edu.qnu.qlsv.services;

import vn.edu.qnu.qlsv.models.SV;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SVService {
    private final String url = "jdbc:mysql://localhost:3306/qlsv_db";
    private final String user = "root";
    private final String password = "";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void add(SV sv) throws SQLException {
        String sql = "INSERT INTO sinh_vien (ma_sv, ho_ten, ngay_sinh, nganh, dtb, lop_sh) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sv.masv());
            pstmt.setString(2, sv.hoten());
            pstmt.setDate(3, new java.sql.Date(sv.ngayThangNamSinh().getTime()));
            pstmt.setString(4, sv.nganh());
            pstmt.setFloat(5, sv.dtb());
            pstmt.setString(6, sv.lopSh());
            pstmt.executeUpdate();
        }
    }

    public boolean removeById(String masv) throws SQLException {
        String sql = "DELETE FROM sinh_vien WHERE ma_sv = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, masv);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean update(SV sv) throws SQLException {
        String sql = "UPDATE sinh_vien SET ho_ten = ?, ngay_sinh = ?, nganh = ?, dtb = ?, lop_sh = ? WHERE ma_sv = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sv.hoten());
            pstmt.setDate(2, new java.sql.Date(sv.ngayThangNamSinh().getTime()));
            pstmt.setString(3, sv.nganh());
            pstmt.setFloat(4, sv.dtb());
            pstmt.setString(5, sv.lopSh());
            pstmt.setString(6, sv.masv());
            return pstmt.executeUpdate() > 0;
        }
    }

    public SV findById(String masv) throws SQLException {
        String sql = "SELECT * FROM sinh_vien WHERE ma_sv = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, masv);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToSV(rs);
            }
        }
        return null;
    }

    public List<SV> findAll() throws SQLException {
        String sql = "SELECT * FROM sinh_vien";
        return executeQuery(sql);
    }

    public List<SV> findByClass(String lopSh) throws SQLException {
        String sql = "SELECT * FROM sinh_vien WHERE lop_sh = ?";
        return executeQuery(sql, lopSh);
    }

    public List<SV> findByMajor(String nganh) throws SQLException {
        String sql = "SELECT * FROM sinh_vien WHERE nganh = ?";
        return executeQuery(sql, nganh);
    }

    public List<SV> sortByGPA() throws SQLException {
        String sql = "SELECT * FROM sinh_vien ORDER BY dtb DESC";
        return executeQuery(sql);
    }

    public List<SV> findByBirthMonth(int month) throws SQLException {
        String sql = "SELECT * FROM sinh_vien WHERE MONTH(ngay_sinh) = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, month);
            ResultSet rs = pstmt.executeQuery();
            List<SV> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapResultSetToSV(rs));
            }
            return list;
        }
    }

    private List<SV> executeQuery(String sql, String... params) throws SQLException {
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }
            ResultSet rs = pstmt.executeQuery();
            List<SV> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapResultSetToSV(rs));
            }
            return list;
        }
    }

    private SV mapResultSetToSV(ResultSet rs) throws SQLException {
        return new SV(
                rs.getString("ma_sv"),
                rs.getString("ho_ten"),
                new java.util.Date(rs.getDate("ngay_sinh").getTime()),
                rs.getString("nganh"),
                rs.getFloat("dtb"),
                rs.getString("lop_sh")
        );
    }
}
