package vn.edu.qnu.qlsv;

import vn.edu.qnu.qlsv.models.SV;
import vn.edu.qnu.qlsv.services.SVService;
import vn.edu.qnu.qlsv.validator.SVValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final SVService svService = new SVService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== QUẢN LÝ SINH VIÊN =====");
            System.out.println("1. Thêm sinh viên");
            System.out.println("2. Xóa sinh viên");
            System.out.println("3. Sửa sinh viên");
            System.out.println("4. In danh sách tất cả sinh viên");
            System.out.println("5. In danh sách sinh viên theo lớp");
            System.out.println("6. In danh sách sinh viên theo ngành");
            System.out.println("7. Sắp xếp sinh viên theo điểm trung bình (giảm dần)");
            System.out.println("8. Tìm sinh viên theo tháng sinh");
            System.out.println("9. Nhập nhanh 10 sinh viên mẫu");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> deleteStudent();
                    case 3 -> updateStudent();
                    case 4 -> printList(svService.findAll());
                    case 5 -> findByClass();
                    case 6 -> findByMajor();
                    case 7 -> printList(svService.sortByGPA());
                    case 8 -> findByMonth();
                    case 9 -> seedData();
                    case 0 -> System.out.println("Tạm biệt!");
                    default -> System.out.println("Lựa chọn không hợp lệ!");
                }
            } catch (Exception e) {
                System.err.println("Lỗi: " + e.getMessage());
            }
        } while (choice != 0);
    }

    private static void addStudent() throws Exception {
        SV sv = inputSV();
        SVValidator.validate(sv);
        svService.add(sv);
        System.out.println("Thêm thành công!");
    }

    private static void deleteStudent() throws Exception {
        System.out.print("Nhập mã SV cần xóa: ");
        String masv = scanner.nextLine();
        if (svService.removeById(masv)) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Không tìm thấy sinh viên có mã: " + masv);
        }
    }

    private static void updateStudent() throws Exception {
        System.out.print("Nhập mã SV cần sửa: ");
        String masv = scanner.nextLine();
        SV existing = svService.findById(masv);
        if (existing == null) {
            System.out.println("Không tìm thấy sinh viên có mã: " + masv);
            return;
        }
        
        System.out.println("Đang sửa sinh viên: " + existing.hoten());
        System.out.println("Nhập thông tin mới (để trống hoặc giữ nguyên ID):");
        SV sv = inputSVWithDefaultId(masv);
        SVValidator.validate(sv);
        if (svService.update(sv)) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }

    private static SV inputSVWithDefaultId(String defaultId) throws ParseException {
        System.out.println("Mã SV: " + defaultId);
        System.out.print("Họ tên: ");
        String hoten = scanner.nextLine();
        System.out.print("Ngày sinh (dd/MM/yyyy): ");
        Date dob = dateFormat.parse(scanner.nextLine());
        System.out.print("Ngành (CNTT/KTPM): ");
        String nganh = scanner.nextLine();
        System.out.print("Điểm TB: ");
        float dtb = Float.parseFloat(scanner.nextLine());
        System.out.print("Lớp sinh hoạt: ");
        String lop = scanner.nextLine();

        return new SV(defaultId, hoten, dob, nganh, dtb, lop);
    }

    private static void findByClass() throws Exception {
        System.out.print("Nhập tên lớp: ");
        String lop = scanner.nextLine();
        printList(svService.findByClass(lop));
    }

    private static void findByMajor() throws Exception {
        System.out.print("Nhập ngành (CNTT/KTPM): ");
        String nganh = scanner.nextLine();
        printList(svService.findByMajor(nganh));
    }

    private static void findByMonth() throws Exception {
        System.out.print("Nhập tháng (1-12): ");
        int month = Integer.parseInt(scanner.nextLine());
        printList(svService.findByBirthMonth(month));
    }

    private static SV inputSV() throws ParseException {
        System.out.print("Mã SV: ");
        String masv = scanner.nextLine();
        System.out.print("Họ tên: ");
        String hoten = scanner.nextLine();
        System.out.print("Ngày sinh (dd/MM/yyyy): ");
        Date dob = dateFormat.parse(scanner.nextLine());
        System.out.print("Ngành (CNTT/KTPM): ");
        String nganh = scanner.nextLine();
        System.out.print("Điểm TB: ");
        float dtb = Float.parseFloat(scanner.nextLine());
        System.out.print("Lớp sinh hoạt: ");
        String lop = scanner.nextLine();

        return new SV(masv, hoten, dob, nganh, dtb, lop);
    }

    private static void printList(List<SV> list) {
        if (list.isEmpty()) {
            System.out.println("Danh sách trống.");
            return;
        }
        System.out.printf("%-12s | %-25s | %-12s | %-6s | %-6s | %-10s\n", 
            "Mã SV", "Họ Tên", "Ngày Sinh", "Ngành", "Điểm", "Lớp");
        System.out.println("-".repeat(85));
        for (SV sv : list) {
            System.out.printf("%-12s | %-25s | %-12s | %-6s | %-6s | %-10s\n",
                sv.masv(), sv.hoten(), dateFormat.format(sv.ngayThangNamSinh()), 
                sv.nganh(), sv.dtb(), sv.lopSh());
        }
    }

    private static void seedData() {
        try {
            SV[] samples = {
                new SV("4551050001", "nguyen van a", dateFormat.parse("01/01/2005"), "CNTT", 8.5f, "K45A"),
                new SV("4551050002", "TRAN THI B", dateFormat.parse("15/05/2004"), "CNTT", 7.0f, "K45A"),
                new SV("4551050003", "Le van c", dateFormat.parse("20/10/2005"), "CNTT", 9.0f, "K45B"),
                new SV("4551090001", "pham nhat vUONG", dateFormat.parse("12/12/2003"), "KTPM", 6.5f, "K45C"),
                new SV("4551090002", "Hoang van d", dateFormat.parse("05/03/2005"), "KTPM", 8.0f, "K45C"),
                new SV("4551050004", "ngo thi e", dateFormat.parse("22/07/2004"), "CNTT", 7.5f, "K45A"),
                new SV("4551090003", "dang le nguyen vu", dateFormat.parse("10/11/2002"), "KTPM", 9.5f, "K45C"),
                new SV("4551050005", "Bui xuan huan", dateFormat.parse("01/04/2005"), "CNTT", 5.5f, "K45B"),
                new SV("4551090004", "truong my lan", dateFormat.parse("15/09/2003"), "KTPM", 8.8f, "K45C"),
                new SV("4551050006", "ly tieu long", dateFormat.parse("27/11/2004"), "CNTT", 10.0f, "K45B")
            };

            for (SV sv : samples) {
                try {
                    SVValidator.validate(sv);
                    svService.add(sv);
                } catch (Exception e) {
                    System.err.println("Không thể thêm SV " + sv.masv() + ": " + e.getMessage());
                }
            }
            System.out.println("Đã nạp 10 sinh viên mẫu!");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
