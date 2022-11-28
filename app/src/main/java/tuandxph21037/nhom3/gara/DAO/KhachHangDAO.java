package tuandxph21037.nhom3.gara.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tuandxph21037.nhom3.gara.DATABASE.XeHelper;
import tuandxph21037.nhom3.gara.Model.KhachHang;

public class KhachHangDAO {
    private SQLiteDatabase db;

    public KhachHangDAO(Context context){
        XeHelper dbHelper = new XeHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(KhachHang obj){
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.hoTen);
        values.put("Tuoi",obj.Tuoi);
        values.put("sdt",obj.sdt);
        return db.insert("KhachHang",null, values);

    }
    public int update(KhachHang obj){
        ContentValues values = new ContentValues();
        values.put("hoTen",obj.hoTen);
        values.put("Tuoi",obj.Tuoi);
        values.put("sdt",obj.sdt);
        return db.update("KhachHang",values,"maKhachHang=?",new String[]{String.valueOf(obj.maKhachHang)});
    }
    public int delete(String id){
        return db.delete("KhachHang","maKhachHang=?",new String[]{id});
    }
    //get tat ca data
    public List<KhachHang> getAll(){
        String sql = "SELECT * FROM KhachHang";
        return getData(sql);
    }
    //get data theo id
    public KhachHang getID(String id){
        String sql = "SELECT * FROM KhachHang WHERE maKhachHang=?";
        List<KhachHang> list = getData(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<KhachHang> getData(String sql, String...selectionArgs) {
        List<KhachHang> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            KhachHang obj = new KhachHang();
            obj.maKhachHang = Integer.parseInt(c.getString(c.getColumnIndex("maKhachHang")));
            obj.hoTen = c.getString(c.getColumnIndex("hoTen"));
            obj.Tuoi = c.getString(c.getColumnIndex("Tuoi"));
            obj.sdt = c.getString(c.getColumnIndex("sdt"));
            list.add(obj);
        }
        return list;
    }
}
