package tuandxph21037.nhom3.gara.Model;

public class Xe {
    public int maXe;
    public String tenXe;
    public int maLoaiXe;
    public byte[] img;
    public String bienSo;
    public int gia;

    public Xe() {
    }


    public Xe(int maXe, String tenXe, int maLoaiXe, byte[] img, String bienSo, int gia) {
        this.maXe = maXe;
        this.tenXe = tenXe;
        this.maLoaiXe = maLoaiXe;
        this.img = img;
        this.bienSo = bienSo;
        this.gia = gia;
    }
}
