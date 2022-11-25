package tuandxph21037.nhom3.gara.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import tuandxph21037.nhom3.gara.Adapter.KhachHangAdapter;
import tuandxph21037.nhom3.gara.DAO.KhachHangDAO;
import tuandxph21037.nhom3.gara.Model.KhachHang;
import tuandxph21037.nhom3.gara.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KhachHangfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KhachHangfragment extends Fragment {
    ListView lvKH;
    ArrayList<KhachHang> list;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaKH,edTen, edTuoi ,edSdt;
    Button btnSave,btnCancel;

    static KhachHangDAO dao;
    KhachHangAdapter adapter;
    KhachHang item;

//    String regex = "/(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})\\b/";
    String regex = "\"^0{1}[0-9]{9}\"";

    public KhachHangfragment() {
        // Required empty public constructor
    }

    public static KhachHangfragment newInstance() {
        KhachHangfragment fragment = new KhachHangfragment();
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
        return inflater.inflate(R.layout.fragment_khachhang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvKH = view.findViewById(R.id.lvKhachHang);
        fab = view.findViewById(R.id.fab);
        dao = new KhachHangDAO(getActivity());
        capNhatLv();
        fab.setOnClickListener(view1 -> {
            openDialog(getActivity(),0);
        });
        lvKH.setOnItemLongClickListener((parent, view1, position, id) -> {
            item = list.get(position);
            openDialog(getActivity(),1);
            return false;
        });
    }

    protected void openDialog(final Context context, final int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.khach_hang_dialog);
        edMaKH = dialog.findViewById(R.id.edMaKH);
        edTen = dialog.findViewById(R.id.edTenKH);
        edTuoi = dialog.findViewById(R.id.edTuoi);
        edSdt = dialog.findViewById(R.id.edSDT);
        btnSave = dialog.findViewById(R.id.btnSaveKH);
        btnCancel = dialog.findViewById(R.id.btnCancelKH);
        edMaKH.setEnabled(false);
        if (type != 0){
            edMaKH.setText(String.valueOf(item.maKhachHang));
            edTen.setText(item.hoTen);
            edTuoi.setText(item.Tuoi);
            edSdt.setText(item.sdt);
        }
        btnCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        btnSave.setOnClickListener(view -> {
            item = new KhachHang();
            item.hoTen = edTen.getText().toString();
            item.Tuoi =edTuoi.getText().toString();
            item.sdt = Integer.parseInt(edSdt.getText().toString());
            if (validate()>0){
                if (type==0){
                    if (dao.insert(item)>0){
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Thêm Thất bại", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    item.maKhachHang = Integer.parseInt(edMaKH.getText().toString());
                    if (dao.update(item)>0){
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                capNhatLv();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    void capNhatLv() {
        list = (ArrayList<KhachHang>) dao.getAll();
        adapter = new KhachHangAdapter(getActivity(),this,list);
        lvKH.setAdapter(adapter);
    }
    public void xoa(final String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dao.delete(Id);
                capNhatLv();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        builder.show();
    }
    public int validate(){
        int check = 1;
        String regexsdt = edSdt.getText().toString();
        if (edTen.getText().toString().length()==0 || edTuoi.getText().toString().length()==0 || edSdt.getText().length()==0){
            Toast.makeText(getContext(), "Bạn Phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else if (edSdt.getText().toString().length()<10 || regexsdt.length()>13 || regexsdt.matches(regex)==false  ){
            Toast.makeText(getActivity(),"Please enter "+"\n"+" valid phone number",Toast.LENGTH_SHORT).show();
        }
        check = -1;
        return check;
    }
}