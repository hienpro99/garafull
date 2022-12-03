package tuandxph21037.nhom3.gara.Model;

import java.util.Date;

public class HoaDon {
    public int maHoaDon;
    public String maNv;
    public int maKhachHang;
    public int maXe;
    public Date ngay;
    public int giaTien;
    public String bienSoHD;

    public HoaDon() {
    }

    public HoaDon(int maHoaDon, String maNv, int maKhachHang, int maXe, Date ngay, int giaTien, String bienSoHD) {
        this.maHoaDon = maHoaDon;
        this.maNv = maNv;
        this.maKhachHang = maKhachHang;
        this.maXe = maXe;
        this.ngay = ngay;
        this.giaTien = giaTien;
        this.bienSoHD = bienSoHD;
    }

}
