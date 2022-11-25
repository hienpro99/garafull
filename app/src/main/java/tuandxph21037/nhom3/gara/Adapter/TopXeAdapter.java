package tuandxph21037.nhom3.gara.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import tuandxph21037.nhom3.gara.R;

import java.util.ArrayList;

import tuandxph21037.nhom3.gara.Model.Top;
import tuandxph21037.nhom3.gara.fragment.TopXe;

public class TopXeAdapter extends ArrayAdapter<Top> {
    private Context context;
    TopXe fragment;
    private ArrayList<Top> lists;
    TextView tvXe, tvSL;

    public TopXeAdapter(@NonNull Context context, TopXe fragment, ArrayList<Top> lists) {
        super(context, 0, lists);
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.top_item, null);
        }

        final Top item = lists.get(position);

        if (item != null) {
            tvXe = v.findViewById(R.id.tvXe);
            tvXe.setText("Xe: "+item.tenXe);
            tvSL = v.findViewById(R.id.tvSL);
            tvSL.setText("Số lượng: "+item.soLuong);
        }
        return v;
    }
}
