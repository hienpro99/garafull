package tuandxph21037.nhom3.gara.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import tuandxph21037.nhom3.gara.Model.NhanVien;
import tuandxph21037.nhom3.gara.R;
import tuandxph21037.nhom3.gara.fragment.NhanVienfragment;

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
    private Context context;
    NhanVienfragment fragment;
    private ArrayList<NhanVien> list;
    TextView tvMaNv,tvTenNV,tvSDT,tvUser,tvPass;
    ImageView imgEdit,imgDelete;

    public NhanVienAdapter(@NonNull Context context, NhanVienfragment fragment , ArrayList<NhanVien> list) {
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
            v = inflater.inflate(R.layout.nhan_vien_item,null);
        }
        final NhanVien item = list.get(position);
        if (item != null){
//            tvMaNv = v.findViewById(R.id.tvMaNv);
//            tvMaNv.setText("Tên đăng nhập: "+item.maNv);

            tvTenNV = v.findViewById(R.id.tvTenNV);
            tvTenNV.setText("Tên Nhân Viên: "+item.tenNhanVien);

            tvSDT = v.findViewById(R.id.tvSDT);
            tvSDT.setText("SDT: "+item.sdt);

            tvMaNv = v.findViewById(R.id.tvMaNv);
            tvMaNv.setText("Tên đăng nhập: "+item.maNv);

            //
//            tvUser = v.findViewById(R.id.tvUser);
//            tvUser.setText("Tên đăng nhập: "+item.User);
            tvPass = v.findViewById(R.id.tvMK);
            tvPass.setText("Mật Khẩu: "+item.matKhau);
            imgDelete = v.findViewById(R.id.imgDelete);

            //
//            imgEdit = v.findViewById(R.id.imgEdit);
            imgDelete = v.findViewById(R.id.imgDelete);
        }
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(String.valueOf(item.maNv));
            }
        });
        return v;
    }
}
