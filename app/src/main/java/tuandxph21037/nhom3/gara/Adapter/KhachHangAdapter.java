package tuandxph21037.nhom3.gara.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import tuandxph21037.nhom3.gara.DAO.HoaDonDAO;
import tuandxph21037.nhom3.gara.DAO.XeDAO;
import tuandxph21037.nhom3.gara.Model.KhachHang;
import tuandxph21037.nhom3.gara.R;
import tuandxph21037.nhom3.gara.fragment.KhachHangfragment;

public class KhachHangAdapter extends ArrayAdapter<KhachHang> {
    private Context context;
    KhachHangfragment fragment;
    private ArrayList<KhachHang> lists;
    TextView tvMaKH,tvTenKH,tvTuoi,tvSDT;
    ImageView imgKhachHang,imgDelete;

    public KhachHangAdapter(@NonNull Context context, KhachHangfragment fragment, ArrayList<KhachHang> lists) {
        super(context, 0, lists);
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.khach_hang_item,null);
        }
        final KhachHang item = lists.get(position);
        if (item != null){
            tvMaKH = v.findViewById(R.id.tvMaKH);
            tvMaKH.setText("Mã Khách Hàng: "+item.maKhachHang);

            tvTenKH = v.findViewById(R.id.tvTenKH);
            tvTenKH.setText("Tên Khách : "+item.hoTen);
            tvTuoi = v.findViewById(R.id.tvTuoi);
            tvTuoi.setText("Tuổi: "+item.Tuoi);
            tvSDT = v.findViewById(R.id.tvSDT);
            tvSDT.setText("SDT: "+item.sdt);
            imgKhachHang = v.findViewById(R.id.imgKhachHang);
            ///ảnh ce
            if (position % 3 == 0) {
                imgKhachHang.setImageResource(R.drawable.user);
            } else {
                imgKhachHang.setImageResource(R.drawable.img_1);
            }
            ///end ảnh
            imgDelete = v.findViewById(R.id.imgDelete);

            //
//            imgEdit = v.findViewById(R.id.imgEdit);
        }
        imgDelete.setOnClickListener(view -> {
            HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
            if(hoaDonDAO.checkKH(String.valueOf(item.maKhachHang)).size()==0){
                fragment.xoa(String.valueOf(item.maKhachHang));
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Cảnh báo!!");
                builder.setMessage("Tồn tại khách hàng trong hóa đơn\n" + "Không thể xóa!");
                builder.setIcon(R.drawable.ic_baseline_warning_24);
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
        //
        return v;
    }
}
