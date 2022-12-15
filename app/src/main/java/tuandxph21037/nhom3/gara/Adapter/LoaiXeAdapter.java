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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import tuandxph21037.nhom3.gara.DAO.XeDAO;
import tuandxph21037.nhom3.gara.Model.LoaiXe;
import tuandxph21037.nhom3.gara.R;
import tuandxph21037.nhom3.gara.fragment.LoaiXefragment;

public class LoaiXeAdapter extends ArrayAdapter<LoaiXe> {
    private Context context;
    LoaiXefragment fragment;
    private ArrayList<LoaiXe> lists;
    TextView tvMaLoaiXe,tvTenLoaiXe;
    ImageView imgLoaiXe,imgDelete;
    XeDAO xeDao;

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

            imgLoaiXe = v.findViewById(R.id.imgLoaiXe);

            ///ảnh ce
            if (position % 3 == 0) {
                imgLoaiXe.setImageResource(R.drawable.loaixe);
            } else {
                imgLoaiXe.setImageResource(R.drawable.hangxe);
            }
            ///end ảnh
        }
        imgDelete.setOnClickListener(view -> {
            xeDao= new XeDAO(context);
            if(xeDao.checkXeLX(String.valueOf(item.maLoaiXe)).size()==0){
                fragment.xoa(String.valueOf(item.maLoaiXe));
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Warning!");
                builder.setMessage("Xe trong Loại xe này đang được đăng bán\n" + "Không thể xóa!");
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

        return v;
    }
}
