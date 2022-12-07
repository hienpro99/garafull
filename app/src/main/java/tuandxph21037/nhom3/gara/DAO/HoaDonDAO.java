package tuandxph21037.nhom3.gara.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import tuandxph21037.nhom3.gara.DATABASE.XeHelper;
import tuandxph21037.nhom3.gara.Model.HoaDon;
import tuandxph21037.nhom3.gara.Model.NhanVien;
import tuandxph21037.nhom3.gara.Model.Top;
import tuandxph21037.nhom3.gara.Model.TopNhanVien;
import tuandxph21037.nhom3.gara.Model.Xe;

public class HoaDonDAO {

    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");

    public HoaDonDAO(Context context){
        this.context = context;
        XeHelper dbHelper = new XeHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(HoaDon obj){
        ContentValues values = new ContentValues();
//        values.put("maNv",obj.maNv);
        values.put("maNv",obj.maNv);
        values.put("maKhachHang",obj.maKhachHang);
        values.put("maXe",obj.maXe);
        values.put("ngay",sdf.format(obj.ngay));
        values.put("giaTien",obj.giaTien);
        values.put("bienSoHD",obj.bienSoHD);
        return db.insert("HoaDon",null, values);

    }
    public int update(HoaDon obj){
        ContentValues values = new ContentValues();
//        values.put("maHoaDon",obj.maHoaDon);
        values.put("maNv",obj.maNv);
        values.put("maKhachHang",obj.maKhachHang);
        values.put("maXe",obj.maXe);
        values.put("ngay",sdf.format(obj.ngay));
        values.put("giaTien",obj.giaTien);
        values.put("bienSoHD",obj.bienSoHD);
        return db.update("HoaDon",values,"maHoaDon=?",new String[]{String.valueOf(obj.maHoaDon)});
    }
    public int delete(String id){
        return db.delete("HoaDon","maHoaDon=?",new String[]{id});
    }
    //get tat ca data
    public List<HoaDon> getAllnv(String maNv){
        String sql = "SELECT * FROM HoaDon WHERE maNv=?";
        return getData(sql,maNv);
    }
    public List<HoaDon> getAllad(){
        String sql = "SELECT * FROM HoaDon GROUP BY maHoaDon ORDER BY maNv ASC";
        return getData(sql);
    }
    //get data theo id
    public HoaDon getID(String id){
        String sql = "SELECT * FROM HoaDon WHERE maHoaDon=?";
        List<HoaDon> list = getData(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<HoaDon> getData(String sql, String...selectionArgs) {
        List<HoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            HoaDon obj = new HoaDon();
            obj.maHoaDon = Integer.parseInt(c.getString(c.getColumnIndex("maHoaDon")));
            obj.maNv = c.getString(c.getColumnIndex("maNv"));
            obj.maKhachHang = Integer.parseInt(c.getString(c.getColumnIndex("maKhachHang")));
            obj.maXe = Integer.parseInt(c.getString(c.getColumnIndex("maXe")));
            obj.giaTien = Integer.parseInt(c.getString(c.getColumnIndex("giaTien")));
            obj.bienSoHD = c.getString(c.getColumnIndex("bienSoHD"));
            try {
                obj.ngay = sdf.parse(c.getString(c.getColumnIndex("ngay")));
            }catch (ParseException e){
                e.printStackTrace();
            }
            list.add(obj);
        }
        return list;
    }
    ///
    //Thống kê top nhân viên
    @SuppressLint("Range")
    public List<TopNhanVien> getTopNhanVien() {
        String sqlNhanVien = "SELECT maNv, count(maNv) as soLuong,SUM(giaTien) as doanhSo FROM HoaDon GROUP BY maNv ORDER BY doanhSo DESC LIMIT 10";
        List<TopNhanVien> list = new ArrayList<TopNhanVien>();
        NhanVienDAO nhanVienDAO = new NhanVienDAO(context);
        Cursor c = db.rawQuery(sqlNhanVien, null);
        while (c.moveToNext()){
            TopNhanVien topNhanVien = new TopNhanVien();
            NhanVien nhanVien = nhanVienDAO.getID(c.getString(c.getColumnIndex("maNv")));
            topNhanVien.tenNhanVien = nhanVien.tenNhanVien;
            topNhanVien.soLuong = Integer.parseInt(c.getString(c.getColumnIndex(("soLuong"))));
            topNhanVien.doanhSo = Integer.parseInt(c.getString(c.getColumnIndex("doanhSo")));
            list.add(topNhanVien);
        }
        return list;
    }
    //thong ke top 10 xe
    @SuppressLint("Range")
    public List<Top> getTop() {
        String sqlTop = "SELECT maXe, count(maXe) as soLuong FROM HoaDon GROUP BY maXe ORDER BY soLuong DESC LIMIT 10";
        List<Top> list = new ArrayList<Top>();
        XeDAO xeDAO = new XeDAO(context);
        Cursor c = db.rawQuery(sqlTop, null);
        while (c.moveToNext()) {
            Top top = new Top();
            Xe xe = xeDAO.getID(c.getString(c.getColumnIndex("maXe")));
            top.tenXe= xe.tenXe;
            top.soLuong=(Integer.parseInt(c.getString(c.getColumnIndex("soLuong"))));
            list.add(top);
        }
        return list;
    }

    //top doanh thu
    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay) {
        String sqlDoanhThu = "SELECT SUM(giaTien) as doanhThu FROM HoaDon WHERE ngay BETWEEN ? AND ?";
        List<Integer> list  = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay});

        while (c.moveToNext()) {
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhThu"))));
            } catch (Exception e) {
                list.add(0);
            }
        }
        return list.get(0);
    }

    @SuppressLint("Range")
    public List<HoaDon> getHoaDonDT(String tuNgay, String denNgay) {
        String sqlhoadon = "SELECT * FROM HoaDon WHERE ngay BETWEEN ? AND ?";
        List<HoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sqlhoadon, new String[]{tuNgay, denNgay});
        while (c.moveToNext()){
            HoaDon obj = new HoaDon();
            obj.maHoaDon = Integer.parseInt(c.getString(c.getColumnIndex("maHoaDon")));
            obj.maNv = c.getString(c.getColumnIndex("maNv"));
            obj.maKhachHang = Integer.parseInt(c.getString(c.getColumnIndex("maKhachHang")));
            obj.maXe = Integer.parseInt(c.getString(c.getColumnIndex("maXe")));
            obj.giaTien = Integer.parseInt(c.getString(c.getColumnIndex("giaTien")));
            obj.bienSoHD = c.getString(c.getColumnIndex("bienSoHD"));
            try {
                obj.ngay = sdf.parse(c.getString(c.getColumnIndex("ngay")));
            }catch (ParseException e){
                e.printStackTrace();
            }
            list.add(obj);
        }
        return list;
    }
    public List<HoaDon> checkXeHD(String maxe){
        String sql = "SELECT * FROM HoaDon WHERE maXe=?";
        List<HoaDon> list = getData(sql, maxe);
        return list;
    }
    public List<HoaDon> getHDNV(String tuNgay, String denNgay, String maNv){
        String sql = "SELECT * FROM HoaDon WHERE ngay BETWEEN ? AND ? AND maNv=?";
        List<HoaDon> list = getData(sql,tuNgay,denNgay, maNv);
        return list;
    }

}
