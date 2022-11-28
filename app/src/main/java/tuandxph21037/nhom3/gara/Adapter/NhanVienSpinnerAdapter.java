package tuandxph21037.nhom3.gara.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import tuandxph21037.nhom3.gara.Model.NhanVien;
import tuandxph21037.nhom3.gara.R;

public class NhanVienSpinnerAdapter extends ArrayAdapter<NhanVien> {
    Context context;
    ArrayList<NhanVien> list;

    TextView tvMaNhanVien,tvTenNhanVien;
    public NhanVienSpinnerAdapter(@NonNull Context context, ArrayList<NhanVien> list) {
        super(context, 0,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.nhan_vien_item_spinner,null);
        }
        final NhanVien  item = list.get(position);
        if (item != null){
//            tvMaNhanVien = v.findViewById(R.id.tvMaNhanVien);
//            tvMaNhanVien.setText(item.maNv + ". ");
            tvTenNhanVien = v.findViewById(R.id.tvTenNhanVien);
            tvTenNhanVien.setText(item.tenNhanVien);
        }
        return v;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v =convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.nhan_vien_item_spinner,null);
        }
        final NhanVien  item = list.get(position);
        if (item != null){
//            tvMaNhanVien = v.findViewById(R.id.tvMaNhanVien);
//            tvMaNhanVien.setText(item.maNv + ".");
            tvTenNhanVien = v.findViewById(R.id.tvTenNhanVien);
            tvTenNhanVien.setText(item.tenNhanVien);
        }
        return v;
    }
}
