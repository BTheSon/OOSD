package vn.edu.qnu.qlsv.models;

import java.util.Date;

/*
* 1) quản lý một danh sách sinh viên,
* mỗi sinh viên có các thông tin sau:
* mã sinh viên,
* họ tên,
* ngày tháng năm sinh,
* ngành đào tạo,
* điểm trung bình,
* lớp sinh hoạt.
* */
public record SV(
        String masv,
        String hoten,
        Date ngayThangNamSinh,
        String nganh,
        float dtb,
        String lopSh
) {
    // Compact Constructor để chuẩn hóa và kiểm tra lần cuối
    public SV {
        hoten = normalizeName(hoten);
    }

    private static String normalizeName(String name) {
        if (name == null || name.isEmpty()) return name;
        String[] words = name.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}
