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

import tuandxph21037.nhom3.gara.Model.TopNhanVien;
import tuandxph21037.nhom3.gara.R;
import tuandxph21037.nhom3.gara.fragment.TopNhanVienFragment;

public class TopNhanVienAdapter extends ArrayAdapter<TopNhanVien> {
    private Context context;
    TopNhanVienFragment fragment;
    TextView tvNhanVienT, tvSL;
    private ArrayList<TopNhanVien> list;

    public TopNhanVienAdapter(@NonNull Context context, TopNhanVienFragment fragment, ArrayList<TopNhanVien> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.top_nhan_vien_item, null);
        }

        final TopNhanVien item = list.get(position);

        if (item != null) {
            tvNhanVienT = v.findViewById(R.id.tvNhanVienT);
            tvNhanVienT.setText("Nhân viên: " + item.tenNhanVien);
            tvSL = v.findViewById(R.id.tvSL);
            tvSL.setText("Số Hóa đơn đã tạo: " + item.soLuong);
        }
        return v;
    }
}
