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

import tuandxph21037.nhom3.gara.Model.LoaiXe;
import tuandxph21037.nhom3.gara.R;
import tuandxph21037.nhom3.gara.fragment.LoaiXefragment;

public class LoaiXeAdapter extends ArrayAdapter<LoaiXe> {
    private Context context;
    LoaiXefragment fragment;
    private ArrayList<LoaiXe> lists;
    TextView tvMaLoaiXe,tvTenLoaiXe;
    ImageView imgEdit,imgDelete;

    public LoaiXeAdapter(@NonNull Context context, LoaiXefragment fragment, ArrayList<LoaiXe> lists) {
        super(context,0,lists);
        this.context = context;
        this.lists = lists;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loai_xe_item,null);
        }
        final LoaiXe item = lists.get(position);
        if (item != null){
            tvMaLoaiXe = v.findViewById(R.id.tvMaLoaiXe);
            tvMaLoaiXe.setText("Mã Loại Xe: "+item.maLoaiXe);

            tvTenLoaiXe = v.findViewById(R.id.tvTenLoaiXe);
            tvTenLoaiXe.setText("Tên Loại Xe: "+item.tenLoai);
            imgDelete = v.findViewById(R.id.imgDelete);
            imgEdit = v.findViewById(R.id.imgEdit);
        }
        imgDelete.setOnClickListener(view -> {
            fragment.xoa(String.valueOf(item.maLoaiXe));
        });
        imgEdit.setOnClickListener(view -> {

        });
        return v;
    }
}
