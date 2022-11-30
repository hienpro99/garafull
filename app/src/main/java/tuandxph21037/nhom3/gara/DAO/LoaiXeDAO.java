package tuandxph21037.nhom3.gara.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tuandxph21037.nhom3.gara.DATABASE.XeHelper;
import tuandxph21037.nhom3.gara.Model.LoaiXe;

public class LoaiXeDAO {
    private SQLiteDatabase db;

    public LoaiXeDAO(Context context){
        XeHelper dbHelper = new XeHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(LoaiXe obj){
        ContentValues values = new ContentValues();
        values.put("tenLoai",obj.tenLoai);
        return db.insert("LoaiXe",null, values);

    }
    public int update(LoaiXe obj){
        ContentValues values = new ContentValues();
        values.put("tenLoai",obj.tenLoai);
        return db.update("LoaiXe",values,"maLoaiXe=?",new String[]{String.valueOf(obj.maLoaiXe)});
    }
    public int delete(String id){
        return db.delete("LoaiXe","maLoaiXe=?",new String[]{id});
    }
    //get tat ca data
    public List<LoaiXe> getAll(){
        String sql = "SELECT * FROM LoaiXe";
        return getData(sql);
    }
    //get data theo id
    public LoaiXe getID(String id){
        String sql = "SELECT * FROM LoaiXe WHERE maLoaiXe=?";
        List<LoaiXe> list = getData(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<LoaiXe> getData(String sql, String...selectionArgs) {
        List<LoaiXe> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            LoaiXe obj = new LoaiXe();
            obj.maLoaiXe = Integer.parseInt(c.getString(c.getColumnIndex("maLoaiXe")));
            obj.tenLoai = c.getString(c.getColumnIndex("tenLoai"));
            list.add(obj);
        }
        return list;
    }


}
