package tuandxph21037.nhom3.gara.Model;

public class NhanVien {
    public String maNv;
    public String tenNhanVien;
    public int sdt;
//    public  String User;
    public String matKhau;

    public NhanVien() {
    }

//    public NhanVien(String maNv, String tenNhanVien, int sdt, String user, String matKhau) {
//        this.maNv = maNv;
//        this.tenNhanVien = tenNhanVien;
//        this.sdt = sdt;
//        User = user;
//        this.matKhau = matKhau;
//    }

    public NhanVien(String maNv, String tenNhanVien, int sdt, String matKhau) {
        this.maNv = maNv;
        this.tenNhanVien = tenNhanVien;
        this.sdt = sdt;
        this.matKhau = matKhau;
    }
}
