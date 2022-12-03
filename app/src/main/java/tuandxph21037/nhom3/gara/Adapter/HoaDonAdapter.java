package tuandxph21037.nhom3.gara.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import tuandxph21037.nhom3.gara.DAO.KhachHangDAO;
import tuandxph21037.nhom3.gara.DAO.NhanVienDAO;
import tuandxph21037.nhom3.gara.DAO.XeDAO;
import tuandxph21037.nhom3.gara.Model.HoaDon;
import tuandxph21037.nhom3.gara.Model.KhachHang;
import tuandxph21037.nhom3.gara.Model.NhanVien;
import tuandxph21037.nhom3.gara.Model.Xe;
import tuandxph21037.nhom3.gara.R;
import tuandxph21037.nhom3.gara.fragment.HoaDonfragment;

public class HoaDonAdapter extends ArrayAdapter<HoaDon> implements Filterable {
    private Context context;
    HoaDonfragment fragment;
    private ArrayList<HoaDon> list;
    //
    private ArrayList<HoaDon> list1;
    TextView tvMaHoaDon,tvBienSoHD, tvTenKH, tvTenXe, tvGiaMua, tvNgaymua, tvTenNhanVien;
    //
    TextView tvGio;
    ImageView imgDelete;
    LinearLayout lineItem;
    XeDAO xeDAO;
    NhanVienDAO nhanVienDAO;
    KhachHangDAO khachHangDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    Calendar now = Calendar.getInstance();
    String strDateFormat24 = "HH:mm:ss a";
    SimpleDateFormat sdg = new SimpleDateFormat(strDateFormat24);

    public HoaDonAdapter(@NonNull Context context, HoaDonfragment fragment, ArrayList<HoaDon> list) {
        super(context,0,list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
        this.list1 = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= inflater.inflate(R.layout.hoa_don_item,null);
        }
        final HoaDon item = list.get(position);
        if (item != null){
            tvMaHoaDon = v.findViewById(R.id.tvMaHoaDon);
            tvMaHoaDon.setText("Mã Hóa đơn: "+item.maHoaDon);

            khachHangDAO = new KhachHangDAO(context);
            KhachHang khachHang = khachHangDAO.getID(String.valueOf(item.maKhachHang));
            tvTenKH = v.findViewById(R.id.tvTenKH);
            tvTenKH.setText("Khách hàng: "+khachHang.hoTen);

            xeDAO = new XeDAO(context);
            Xe xe = xeDAO.getID(String.valueOf(item.maXe));
            tvTenXe = v.findViewById(R.id.tvTenXe);
            tvTenXe.setText("Tên Xe: "+xe.tenXe);

            tvTenNhanVien= v.findViewById(R.id.tvTenNv);
            tvTenNhanVien.setText("Nhân Viên: "+item.maNv);

            tvBienSoHD = v.findViewById(R.id.tvBienSoHD);
            tvBienSoHD.setText("Biển số: " + item.bienSoHD);

            tvGiaMua = v.findViewById(R.id.tvGiaMua);
            tvGiaMua.setText("Giá Mua: "+item.giaTien);

            tvNgaymua = v.findViewById(R.id.tvngaymua);
            tvNgaymua.setText("Ngày Mua: "+sdf.format(item.ngay));

//            tvGio = v.findViewById(R.id.tvGio);
//            tvGio.setText(("Giờ Tạo Hóa Đơn: "+sdg.format(now.getTime())));
            if (item.giaTien >=5000000){
                tvMaHoaDon.setTextColor(Color.RED);
                tvGiaMua.setTextColor(Color.RED);
                v.findViewById(R.id.lineitem).setBackgroundColor(Color.YELLOW);

            }else {
                tvMaHoaDon.setTextColor(Color.BLUE);
                tvGiaMua.setTextColor(Color.BLUE);
                v.findViewById(R.id.lineitem).setBackgroundColor(Color.YELLOW);
            }
            imgDelete = v.findViewById(R.id.imgDelete);
        }
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.xoa(String.valueOf(item.maHoaDon));
            }
        });
        return v;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    list = list1;
                }else {
                    ArrayList<HoaDon> list = new ArrayList<>();
                    for (HoaDon hoaDon : list1){
                        list.add(hoaDon);
                    }
                    list = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<HoaDon>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
