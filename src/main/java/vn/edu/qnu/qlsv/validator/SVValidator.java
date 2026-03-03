package vn.edu.qnu.qlsv.validator;
import vn.edu.qnu.qlsv.models.SV;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class SVValidator {

    // Regex kiểm tra mã sinh viên 10 chữ số
    private static final String CNTT_PREFIX = "455105";
    private static final String KTPM_PREFIX = "455109";
    private static final String MSSV_REGEX = "^\\d{10}$";

    public static void validate(SV sv) throws IllegalArgumentException {
        if (sv == null) throw new IllegalArgumentException("Đối tượng SV không được null");

        // 2. Kiểm tra ngành đào tạo
        if (!sv.nganh().equals("CNTT") && !sv.nganh().equals("KTPM")) {
            throw new IllegalArgumentException("Ngành phải là 'CNTT' hoặc 'KTPM'");
        }

        // 3. Kiểm tra mã sinh viên (MSSV)
        if (!Pattern.matches(MSSV_REGEX, sv.masv())) {
            throw new IllegalArgumentException("Mã sinh viên phải có đúng 10 chữ số");
        }
        if (sv.nganh().equals("CNTT") && !sv.masv().startsWith(CNTT_PREFIX)) {
            throw new IllegalArgumentException("MSSV ngành CNTT phải bắt đầu bằng " + CNTT_PREFIX);
        }
        if (sv.nganh().equals("KTPM") && !sv.masv().startsWith(KTPM_PREFIX)) {
            throw new IllegalArgumentException("MSSV ngành KTPM phải bắt đầu bằng " + KTPM_PREFIX);
        }

        // 4. Kiểm tra điểm trung bình
        if (sv.dtb() < 0.0f || sv.dtb() > 10.0f) {
            throw new IllegalArgumentException("Điểm trung bình phải nằm trong đoạn [0.0, 10.0]");
        }

        // 5. Kiểm tra tuổi (15 - 110)
        validateAge(sv.ngayThangNamSinh());
    }

    private static void validateAge(Date dob) {
        if (dob == null) throw new IllegalArgumentException("Ngày sinh không được để trống");

        Calendar now = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(dob);

        int age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);

        // Điều chỉnh nếu chưa tới sinh nhật trong năm hiện tại
        if (now.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        if (age < 15 || age > 110) {
            throw new IllegalArgumentException("Tuổi sinh viên không hợp lệ (phải từ 15 đến 110). Tuổi hiện tại: " + age);
        }
    }

}