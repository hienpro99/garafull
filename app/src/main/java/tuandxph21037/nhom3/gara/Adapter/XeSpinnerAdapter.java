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

import tuandxph21037.nhom3.gara.Model.Xe;
import tuandxph21037.nhom3.gara.R;

public class XeSpinnerAdapter extends ArrayAdapter<Xe> {
    private Context context;
    private ArrayList<Xe> list;
    TextView tvMaXe,tvTenXe;
    public XeSpinnerAdapter(@NonNull Context context, ArrayList<Xe> list) {
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
            v = inflater.inflate(R.layout.xe_item_spinner,null);
        }
        final Xe item = list.get(position);
        if (item != null){
            tvMaXe = v.findViewById(R.id.tvMaXeSp);
            tvMaXe.setText(item.maXe +". ");
            tvTenXe = v.findViewById(R.id.tvTenXeSp);
            tvTenXe.setText(item.tenXe);
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.xe_item_spinner,null);
        }
        final Xe item = list.get(position);
        if (item != null){
            tvMaXe = v.findViewById(R.id.tvMaXeSp);
            tvMaXe.setText(item.maXe +". ");
            tvTenXe = v.findViewById(R.id.tvTenXeSp);
            tvTenXe.setText(item.tenXe);
        }
        return v;
    }
}
