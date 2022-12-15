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

import tuandxph21037.nhom3.gara.Model.LoaiXe;
import tuandxph21037.nhom3.gara.R;

public class LoaiXeSpinnerAdapter extends ArrayAdapter<LoaiXe> {
    Context context;
    ArrayList<LoaiXe> list;

    TextView tvMaLoaiXe,tvTenLoaiXe;
    public LoaiXeSpinnerAdapter(@NonNull Context context, ArrayList<LoaiXe> list) {
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
            v= inflater.inflate(R.layout.loai_xe_item_spinner,null);
        }
        final LoaiXe  item = list.get(position);
        //lấy dữ liệu từ class loại xe
        if (item != null){
            tvMaLoaiXe = v.findViewById(R.id.tvMaLoaiXeSp);
            tvMaLoaiXe.setText(item.maLoaiXe + ".");
            tvTenLoaiXe = v.findViewById(R.id.tvTenLoaiXeSp);
            tvTenLoaiXe.setText(item.tenLoai);
        }
        return v;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v =convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.loai_xe_item_spinner,null);
        }
        final LoaiXe  item = list.get(position);
        if (item != null){
            tvMaLoaiXe = v.findViewById(R.id.tvMaLoaiXeSp);
            tvMaLoaiXe.setText(item.maLoaiXe + ".");
            tvTenLoaiXe = v.findViewById(R.id.tvTenLoaiXeSp);
            tvTenLoaiXe.setText(item.tenLoai);
        }
        return v;
    }
}
