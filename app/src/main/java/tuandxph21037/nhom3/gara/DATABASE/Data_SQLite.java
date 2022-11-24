package tuandxph21037.nhom3.gara.DATABASE;
public class Data_SQLite {
    public static final String INSERT_THU_THU = "Insert into ThuThu(maTT, hoten, matkhau) values" +
            "('admin', 'Nguyễn Hữu Thiêm', 'admin')," +
            "('thuthu1', 'Thiêm Hữu Nguyễn', '123')";

    public static final String INSERT_THANH_VIEN = "insert into ThanhVien(hoten, namSinh,gioiTinh_PH19987) values" +
            "('Nguyễn Bình Thuận ', '2003',1)," +
            "('Hoàng Nam Tôn ', '2003',0)," +
            "('Mai Văn Quỳ ', '2003',1)," +
            "('Trần Văn Đa ', '2003',0)," +
            "('Hoàng Thị Ngọc ', '2003',0)," +
            "('Trần Văn Trung ', '2003',0)," +
            "('Bá Quốc Mạnh ', '2003',1)," +
            "('Nguyễn Bình ', '2003',0)," +
            "('Lê Văn Quang ', '2003',1)";

    public static final String INSERT_LOAI_SACH ="insert into LoaiSach(tenLoai) values" +
            "('Java')," +
            "('C')," +
            "('C++')," +
            "('HTML/CSS')," +
            "('Javascript')," +
            "('Lập Trình')";

    public static final String INSERT_SACH ="insert into Sach(tenSach, giaThue, maLoai, soLuong_PH19987 ) values" +
            "('Lập Trình cơ bản', 20000, 1,5)," +
            "('Lập trình nâng cao', 15000, 2,10)," +
            "('Nhập môn lập trình', 60000, 3,5)," +
            "('Xây dựng trang web', 10000, 4,4)," +
            "('Thiết kế web', 25000, 5,2)," +
            "('Android cơ bản', 100000, 6,7)";

    public static final String INSERT_PHIEU_MUON="insert into PhieuMuon(maTT, maTV, maSach, tienThue, ngay, traSach) values " +
            "('thuthu1', 1, 2, 25000, '17/10/2022', 0)," +
            "('thuthu1', 2, 3, 20000, '18/10/2022', 1)," +
            "('thuthu1', 3, 6, 1600, '20/10/2022', 0)," +
            "('thuthu1', 4, 4, 2000, '21/10/2022', 1)," +
            "('thuthu1', 5, 2, 19000, '25/10/2022', 0)";
}
