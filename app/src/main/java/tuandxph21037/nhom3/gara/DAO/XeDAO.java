package tuandxph21037.nhom3.gara.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tuandxph21037.nhom3.gara.DATABASE.XeHelper;
import tuandxph21037.nhom3.gara.Model.HoaDon;
import tuandxph21037.nhom3.gara.Model.Xe;

public class XeDAO {
    private SQLiteDatabase db;

    public XeDAO(Context context){
        XeHelper dbHelper = new XeHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(Xe obj){
        ContentValues values = new ContentValues();
        values.put("maLoaiXe",obj.maLoaiXe);
        values.put("tenXe",obj.tenXe);

        values.put("img",obj.img);
        values.put("bienSo",obj.bienSo);

        values.put("gia",obj.gia);
        return db.insert("Xe",null, values);

    }
    public int update(Xe obj){
        ContentValues values = new ContentValues();
        values.put("maLoaiXe",obj.maLoaiXe);
        values.put("tenXe",obj.tenXe);
        values.put("img",obj.img);
        values.put("bienSo",obj.bienSo);
        values.put("gia",obj.gia);
        return db.update("Xe",values,"maXe=?",new String[]{String.valueOf(obj.maXe)});
    }
    public int delete(String id){
        return db.delete("Xe","maXe=?",new String[]{id});
    }
    //get tat ca data
    public List<Xe> getAll(){
        String sql = "SELECT * FROM Xe";
        return getData(sql);
    }
    //get data theo id
    public Xe getID(String id){
        String sql = "SELECT * FROM Xe WHERE maXe=?";
        List<Xe> list = getData(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<Xe> getData(String sql, String...selectionArgs) {
        List<Xe> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            Xe obj = new Xe();
            obj.maXe = Integer.parseInt(c.getString(c.getColumnIndex("maXe")));
            obj.maLoaiXe = Integer.parseInt(c.getString(c.getColumnIndex("maLoaiXe")));
            obj.tenXe = c.getString(c.getColumnIndex("tenXe"));
            obj.img = c.getBlob(c.getColumnIndex("img"));
            obj.bienSo = (c.getString(c.getColumnIndex("bienSo")));
            obj.gia = Integer.parseInt(c.getString(c.getColumnIndex("gia")));


            list.add(obj);
        }
        return list;
    }
    public Xe checkXeLX(String maloaixe){
        String sql = "SELECT * FROM Xe WHERE maLoaiXe=?";
        List<Xe> list = getData(sql, maloaixe);
        return list.get(0);
    }

}
