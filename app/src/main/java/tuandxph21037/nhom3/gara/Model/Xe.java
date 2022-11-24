package tuandxph21037.nhom3.gara.Model;

public class Xe {
    public int maXe;
    public String tenXe;
    public int maLoaiXe;
    public byte[] img;
    public int soLuong;
    public int gia;

    public Xe() {
    }


    public Xe(int maXe, String tenXe, int maLoaiXe, byte[] img, int soLuong, int gia) {
        this.maXe = maXe;
        this.tenXe = tenXe;
        this.maLoaiXe = maLoaiXe;
        this.img = img;
        this.soLuong = soLuong;
        this.gia = gia;
    }
}
