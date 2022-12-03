package tuandxph21037.nhom3.gara.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import tuandxph21037.nhom3.gara.DAO.HoaDonDAO;
import tuandxph21037.nhom3.gara.DAO.LoaiXeDAO;
import tuandxph21037.nhom3.gara.DAO.XeDAO;
import tuandxph21037.nhom3.gara.Model.LoaiXe;
import tuandxph21037.nhom3.gara.Model.Xe;
import tuandxph21037.nhom3.gara.R;
import tuandxph21037.nhom3.gara.fragment.Xefragment;

public class XeAdapter extends ArrayAdapter<Xe> {
    Context context;
    Xefragment fragment;
    List<Xe> list;
    TextView tvmaXe,tvLoaiXe,tvTenXe,tvSoKhungSoMay,tvSoluong,tvGiaMua;
    ImageView imgEdit,imgDelete;
    ImageView imgXe;
    HoaDonDAO hdDao ;
    public XeAdapter(@NonNull Context context, Xefragment fragment, List<Xe> list) {
        super(context, 0,list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.xe_item,null);
        }
        final Xe item = list.get(position);
        if (item != null){
            LoaiXeDAO loaiXeDAO = new LoaiXeDAO(context);
            LoaiXe loaiXe = loaiXeDAO.getID(String.valueOf(item.maLoaiXe));
            tvmaXe = v.findViewById(R.id.tvMaXe);
            tvmaXe.setText("Mã Xe: "+item.maXe);

            imgXe = v.findViewById(R.id.imgXe);
            byte[] img = item.img;
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            imgXe.setImageBitmap(bitmap);

            tvLoaiXe = v.findViewById(R.id.tvLoaiXe);
            tvLoaiXe.setText("Loại Xe: "+loaiXe.tenLoai);
            tvTenXe = v.findViewById(R.id.tvTenXe);
            tvTenXe.setText("Tên Xe: "+item.tenXe);
            tvSoKhungSoMay = v.findViewById(R.id.tvSoKhungSoMay);
            tvSoKhungSoMay.setText("Số khung-số máy: " + item.soKhungSoMay);
            tvSoluong = v.findViewById(R.id.tvSoluong);
            tvSoluong.setText("Số lượng: " +item.soLuong);
            tvGiaMua = v.findViewById(R.id.tvGiaMua);
            tvGiaMua.setText("Giá : "+item.gia);
            imgDelete = v.findViewById(R.id.imgDelete);
        }
        imgDelete.setOnClickListener(view -> {
            hdDao= new HoaDonDAO(context);
            if(hdDao.checkXeHD(String.valueOf(item.maXe))==null){
                fragment.xoa(String.valueOf(item.maLoaiXe));
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Warning!");
                builder.setMessage("Có hóa đơn\n" + "Không thể xóa!");
                builder.setIcon(R.drawable.ic_baseline_delete_24);
                builder.setCancelable(true);
                builder.setPositiveButton("Đã hiểu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
        if( item.soLuong==0){
            tvSoluong.setText("Hết Hàng");
        }
        return v;
    }
}
