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

import tuandxph21037.nhom3.gara.Model.KhachHang;
import tuandxph21037.nhom3.gara.R;

public class KhachHangSpinnerAdapter extends ArrayAdapter<KhachHang> {
    Context context;
    ArrayList<KhachHang> list;
    TextView tvMaKhachHang, tvTenKhachHang;


    public KhachHangSpinnerAdapter(@NonNull Context context, ArrayList<KhachHang> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v==null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.khach_hang_item_spinner, null);
        }
        final KhachHang item = list.get(position);
        if (item != null) {
            tvMaKhachHang = v.findViewById(R.id.tvMaKhachHang);
            tvMaKhachHang.setText(item.maKhachHang + ". ");

            tvTenKhachHang = v.findViewById(R.id.tvTenKhachHang);
            tvTenKhachHang.setText(item.hoTen);

        }
        return v;
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.khach_hang_item_spinner, null);
        }
        final KhachHang item = list.get(position);
        if (item != null) {
            tvMaKhachHang = v.findViewById(R.id.tvMaKhachHang);
            tvMaKhachHang.setText(item.maKhachHang + ". ");

            tvTenKhachHang = v.findViewById(R.id.tvMaKhachHang);
            tvTenKhachHang.setText(item.hoTen);

        }
        return v;
    }
}
