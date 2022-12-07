package tuandxph21037.nhom3.gara.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tuandxph21037.nhom3.gara.Adapter.HoaDonAdapter;
import tuandxph21037.nhom3.gara.Adapter.KhachHangSpinnerAdapter;
import tuandxph21037.nhom3.gara.Adapter.NhanVienSpinnerAdapter;
import tuandxph21037.nhom3.gara.Adapter.XeSpinnerAdapter;
import tuandxph21037.nhom3.gara.DAO.HoaDonDAO;
import tuandxph21037.nhom3.gara.DAO.KhachHangDAO;
import tuandxph21037.nhom3.gara.DAO.NhanVienDAO;
import tuandxph21037.nhom3.gara.DAO.XeDAO;
import tuandxph21037.nhom3.gara.LoginActivity2;
import tuandxph21037.nhom3.gara.MainActivity;
import tuandxph21037.nhom3.gara.ManGiaoDienActivity;
import tuandxph21037.nhom3.gara.Model.HoaDon;
import tuandxph21037.nhom3.gara.Model.KhachHang;
import tuandxph21037.nhom3.gara.Model.NhanVien;
import tuandxph21037.nhom3.gara.Model.Xe;
import tuandxph21037.nhom3.gara.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HoaDonfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HoaDonfragment extends Fragment {
    ManGiaoDienActivity mActivity2;
    ListView lvHoaDon;
    EditText edMaHoaDon;
    TextView tvNgayMua, tvTenNhanVien;
    Spinner spTenKH,spTenXe;
    TextView tvGia,tvBienSoHDDL;
    Button btnadd,btnclose;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    HoaDon item;
    ArrayList<HoaDon> list;
    static HoaDonDAO dao;
    HoaDonAdapter adapter;

    FloatingActionButton fab;
    Dialog dialog;
    //
    NhanVienSpinnerAdapter nhanVienSpinnerAdapter;
    ArrayList<NhanVien> listNhanVien;
    NhanVienDAO nhanVienDAO;
    NhanVien nhanVien;
    String maNhanVien;
    //

    KhachHangSpinnerAdapter khachHangSpinnerAdapter;
    ArrayList<KhachHang> lisKhachHang;
    KhachHangDAO khachHangDAO;
    KhachHang khachHang;
    int maKH;

    XeSpinnerAdapter xeSpinnerAdapter;
    ArrayList<Xe> listXe;
    XeDAO xeDAO;
    Xe Xe;
    String bienSo;
    int maXe,Gia;
    int positionKH,positionXe;
    String tennv="";
    public HoaDonfragment() {
        // Required empty public constructor
    }


    public static HoaDonfragment newInstance() {
        HoaDonfragment fragment = new HoaDonfragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hoadon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity2 = (ManGiaoDienActivity) getActivity();
        String tennv = mActivity2.getTennv();
        Toast.makeText(mActivity2, ""+tennv, Toast.LENGTH_SHORT).show();
        lvHoaDon = view.findViewById(R.id.lvHoaDon);
        fab = view.findViewById(R.id.fab);
        dao = new HoaDonDAO(getActivity());
        xeDAO= new XeDAO(getActivity());
        khachHangDAO = new KhachHangDAO(getActivity());
        TextInputEditText edSearch = view.findViewById(R.id.searchNv);
        ImageView search = view.findViewById(R.id.search);
        capNhatLv();
        if(!tennv.equals("admin")){
            search.setEnabled(false);
            edSearch.setEnabled(false);
        }

        NhanVienDAO nvDao = new NhanVienDAO(getActivity());
        search.setOnClickListener(view1->{

            if(edSearch.getText().toString().equals("")){
                list = (ArrayList<HoaDon>) dao.getAllad();
                adapter = new HoaDonAdapter(getActivity(),this,list);
                lvHoaDon.setAdapter(adapter);
            }else if(nvDao.getTenNv(edSearch.getText().toString()).size()==0){
                Toast.makeText(getActivity(), "Không có nhân viên phù hợp", Toast.LENGTH_SHORT).show();
            }else {
                HdNV(edSearch.getText().toString());
            }
        });
        if(tennv.equals("admin")){
            fab.setVisibility(View.INVISIBLE);
        }
        fab.setOnClickListener(view1 -> {
            if(xeDAO.getAll().size()==0){
                Toast.makeText(getActivity(), "Bạn cần thêm Xe trước khi tạo hóa đơn", Toast.LENGTH_SHORT).show();
            }else if (khachHangDAO.getAll().size()==0){
                Toast.makeText(getActivity(), "Bạn cần thêm Khách hàng trước khi tạo hóa đơn", Toast.LENGTH_SHORT).show();
            } else{
                openDialog(getActivity(), 0);
            }
        });
        lvHoaDon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!tennv.equals("admin")){
                    item = list.get(i);
                    openDialog(getActivity(),1);
                }

                return false;
            }
        });
    }
    void capNhatLv() {
        mActivity2 = (ManGiaoDienActivity) getActivity();
        String tennv = mActivity2.getTennv();
        if(tennv.equals("admin")){
            list = (ArrayList<HoaDon>) dao.getAllad();
            adapter = new HoaDonAdapter(getActivity(),this,list);
            lvHoaDon.setAdapter(adapter);
        }else{
            list = (ArrayList<HoaDon>) dao.getAllnv(tennv);
            adapter = new HoaDonAdapter(getActivity(),this,list);
            lvHoaDon.setAdapter(adapter);
        }

    }
    void HdNV(String manv) {
        mActivity2 = (ManGiaoDienActivity) getActivity();
        String tennv = mActivity2.getTennv();
        list = (ArrayList<HoaDon>) dao.getAllnv(manv);
        adapter = new HoaDonAdapter(getActivity(),this,list);
        lvHoaDon.setAdapter(adapter);

    }
    public void xoa(final String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("bạn có muốn xóa không?");
        builder.setIcon(R.drawable.ic_baseline_delete_24);
        builder.setCancelable(true);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dao.delete(Id);
                capNhatLv();
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }

    protected void openDialog(final Context context, final int type){
        String tennv = mActivity2.getTennv();
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.hoa_don_dialog);
        edMaHoaDon = dialog.findViewById(R.id.edMaHoaDon);
        tvBienSoHDDL = dialog.findViewById(R.id.tvBienSoHDDL);
        tvTenNhanVien= dialog.findViewById(R.id.tvTenNV);
        nhanVienDAO= new NhanVienDAO(getActivity());
        NhanVien nv = new NhanVien();
        if(!tennv.equals("admin")) {
            nv =  nhanVienDAO.getID(""+tennv);
        }
        tvTenNhanVien.setText(nv.tenNhanVien);
        spTenKH = dialog.findViewById(R.id.spTenKH);
        spTenXe = dialog.findViewById(R.id.spTenXe);
        tvNgayMua = dialog.findViewById(R.id.tvNgay);
        tvGia = dialog.findViewById(R.id.tvGiaMua);
        btnclose = dialog.findViewById(R.id.btnCancel);
        btnadd = dialog.findViewById(R.id.btnSave);
        tvNgayMua.setText("Ngày Mua: " + sdf.format(new Date()));

//        nhanVienDAO = new NhanVienDAO(context);
//        listNhanVien = new ArrayList<NhanVien>();
//        listNhanVien = (ArrayList<NhanVien>)nhanVienDAO.getAll();
//        nhanVienSpinnerAdapter = new NhanVienSpinnerAdapter(context,listNhanVien);
//        spTenNhanVien.setAdapter(nhanVienSpinnerAdapter);
//        spTenNhanVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                maNhanVien = String.valueOf(listNhanVien.get(position).maNv);
//                Toast.makeText(context,"Nhân Viên: " + listNhanVien.get(position).tenNhanVien,Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        khachHangDAO = new KhachHangDAO(context);
        lisKhachHang = new ArrayList<KhachHang>();
        lisKhachHang = (ArrayList<KhachHang>) khachHangDAO.getAll();
        khachHangSpinnerAdapter = new KhachHangSpinnerAdapter(context,lisKhachHang);
        spTenKH.setAdapter(khachHangSpinnerAdapter);
        spTenKH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maKH = lisKhachHang.get(i).maKhachHang;
                Toast.makeText(context, "Khách hàng: "+lisKhachHang.get(i).hoTen, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        xeDAO = new XeDAO(context);
        listXe = new ArrayList<Xe>();
        listXe = (ArrayList<Xe>) xeDAO.getAll();
        xeSpinnerAdapter = new XeSpinnerAdapter(context,listXe);
        spTenXe.setAdapter(xeSpinnerAdapter);
        spTenXe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maXe = listXe.get(i).maXe;
                Gia= listXe.get(i).gia;
                bienSo = listXe.get(i).bienSo;
                tvBienSoHDDL.setText("Biển số: " + bienSo);
                tvGia.setText("Giá tiền: "+Gia);
                Toast.makeText(context, "Xe: "+listXe.get(i).tenXe, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnclose.setOnClickListener(view -> {
            dialog.dismiss();
        });
        edMaHoaDon.setEnabled(false);
        if (type !=0){

            edMaHoaDon.setText(String.valueOf(item.maHoaDon));
            for (int i = 0; i<lisKhachHang.size();i++)
                if (item.maKhachHang == (lisKhachHang.get(i).maKhachHang)){
                    positionKH = i;
                }
            spTenKH.setSelection(positionKH);

            for (int i = 0; i<listXe.size();i++)
                if (item.maXe == (listXe.get(i).maXe)){
                    positionXe = i;
                }
            spTenXe.setSelection(positionXe);
            tvTenNhanVien.setText(nv.tenNhanVien);
            tvNgayMua.setText("Ngày Thuê: "+sdf.format(item.ngay));
            tvGia.setText("Giá Tiền: "+item.giaTien);
            tvBienSoHDDL.setText("Biển số: " + item.bienSoHD);
        }
        NhanVien finalNv = nv;
        btnadd.setOnClickListener(view -> {
//            nhanVienDAO= new NhanVienDAO(getActivity());
//            NhanVien nv =  nhanVienDAO.getTenNv((String) tvTenNhanVien.getText());

            item = new HoaDon();
            item.maXe = maXe;
            item.maNv = finalNv.maNv;
            item.maKhachHang = maKH;
            item.ngay = new Date();
            item.giaTien = Gia;
            item.bienSoHD = bienSo;
            HoaDonDAO hddao = new HoaDonDAO(getActivity());


            if (type == 0) {
                if(hddao.checkXeHD(String.valueOf(maXe)).size()==0){
                    if (dao.insert(item) > 0) {
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Xe đã có người mua", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }else{ if (hddao.checkXeHD(String.valueOf(maXe)).size()==0) {
                item.maHoaDon = Integer.parseInt(edMaHoaDon.getText().toString());
                if (dao.update(item) > 0) {
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
//                     }else Toast.makeText(context, "Xe đã có người mua", Toast.LENGTH_SHORT).show();

            }


            capNhatLv();
            dialog.dismiss();

        });
        dialog.show();
    }

    public String getTennv() {
        mActivity2 = (ManGiaoDienActivity) getActivity();
        String tennv = mActivity2.getTennv();
        return tennv;
    }
}