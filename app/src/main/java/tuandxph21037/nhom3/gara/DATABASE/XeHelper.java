package tuandxph21037.nhom3.gara.DATABASE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class XeHelper extends SQLiteOpenHelper {
    static final String dbName="Gara";
    static final int dbVersion=1;
    public XeHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableNhanVien="create table NhanVien (" +
                "maNv TEXT PRIMARY KEY, " +
                "tenNhanVien TEXT NOT NULL, " +
                "sdt INTERGER NOT NULL," +
//                "User TEXT NOT NULL, "+
                "matKhau TEXT NOT NULL) ";
        db.execSQL(createTableNhanVien);

        String createTableKhachHang="create table KhachHang (" +
                "maKhachHang INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hoTen text NOT NULL, " +
                "Tuoi text NOT NULL, " +
                "sdt INTERGER NOT NULL)";
        db.execSQL(createTableKhachHang);

        String createTableLoaiXe="create table LoaiXe (" +
                "maLoaiXe INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenLoai text NOT NULL)";
        db.execSQL(createTableLoaiXe);

        String createTableXe="create table Xe (" +
                "maXe INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenXe text NOT NULL," +
                "maLoaiXe INTEGER REFERENCES LoaiXe(maLoaiXe)," +
                "img BLOB NOT NULL," +
                "soLuong INTEGER NOT NULL, " +
                "gia INTEGER NOT NULL )";
        db.execSQL(createTableXe);

        String createTableHoaDon="create table HoaDon (" +
                "maHoaDon INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maNv TEXT REFERENCES NhanVien(maNv), " +
                "maKhachHang INTEGER REFERENCES KhachHang(maKhachHang), " +
                "maXe INTEGER REFERENCES Xe(maXe), " +
                "ngay DATE NOT NULL, " +
                "giaTien INTERGER NOT NULL)";
        db.execSQL(createTableHoaDon);
        db.execSQL(INSERT_NHAN_VIEN);

    }
    public static final String INSERT_NHAN_VIEN = "insert into NhanVien(maNv,tenNhanVien,sdt,matKhau) values" +
            "('admin','hienadmin','0923657778','admin')";

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropTableNhanVien = "drop table if exists NhanVien";
        db.execSQL(dropTableNhanVien);
        String dropTableKhachHang = "drop table if exists KhachHang";
        db.execSQL(dropTableKhachHang);
        String dropTableLoaiXe = "drop table if exists LoaiXe";
        db.execSQL(dropTableLoaiXe);
        String dropTableXe = "drop table if exists Xe";
        db.execSQL(dropTableXe);
        String dropTableHoaDon = "drop table if exists HoaDon";
        db.execSQL(dropTableHoaDon);
    }
}
