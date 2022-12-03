package tuandxph21037.nhom3.gara.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tuandxph21037.nhom3.gara.DATABASE.XeHelper;
import tuandxph21037.nhom3.gara.Model.NhanVien;

public class NhanVienDAO {
    private SQLiteDatabase db;

    public NhanVienDAO(Context context) {
        XeHelper xeHelper = new XeHelper(context);
        db = xeHelper.getWritableDatabase();
    }
    public long insert(NhanVien obj){
        ContentValues values = new ContentValues();
        values.put("maNv",obj.maNv);
        values.put("tenNhanVien",obj.tenNhanVien);
        values.put("sdt",obj.sdt);
//        values.put("User",obj.User);
        values.put("matKhau",obj.matKhau);
        return db.insert("NhanVien",null, values);

    }
    public int updatePass(NhanVien obj){
        ContentValues values = new ContentValues();
//        values.put("maNv",obj.maNv);
        values.put("tenNhanVien",obj.tenNhanVien);
        values.put("sdt",obj.sdt);
//        values.put("User",obj.User);
        values.put("matKhau",obj.matKhau);
        return db.update("NhanVien",values,"maNv=?",new String[]{String.valueOf(obj.maNv)});
    }
    public int delete(String id){
        return db.delete("NhanVien","maNv=?",new String[]{id});
    }
    //get tat ca data
    public List<NhanVien> getAll(){
        String sql = "SELECT * FROM NhanVien";
        return getData(sql);
    }
    //get data theo id
    public NhanVien getID(String id){
        String sql = "SELECT * FROM NhanVien WHERE maNv=?";
        List<NhanVien> list = getData(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<NhanVien> getData(String sql, String...selectionArgs) {
        List<NhanVien> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            NhanVien obj = new NhanVien();
            obj.maNv = c.getString(c.getColumnIndex("maNv"));
            obj.tenNhanVien = c.getString(c.getColumnIndex("tenNhanVien"));
            obj.sdt = c.getString(c.getColumnIndex("sdt"));
//            obj.User = c.getString(c.getColumnIndex("User"));
            obj.matKhau = c.getString(c.getColumnIndex("matKhau"));
            list.add(obj);
        }
        return list;
    }


    public int checkLogin(String id, String password) {
        String sql = "SELECT * FROM NhanVien WHERE maNv=? AND matKhau=?";
        List<NhanVien> list = getData(sql,id,password);
        if (list.size() == 0)
            return -1;
        return 1;

    }
    public NhanVien getTenNv(String name){
        String sql = "SELECT * FROM NhanVien WHERE maNv=?";
        List<NhanVien> list = getData(sql, name);
        return list.get(0);
    }
}
