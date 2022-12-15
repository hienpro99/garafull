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
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import tuandxph21037.nhom3.gara.Adapter.NhanVienAdapter;
import tuandxph21037.nhom3.gara.DAO.NhanVienDAO;
import tuandxph21037.nhom3.gara.Model.NhanVien;
import tuandxph21037.nhom3.gara.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NhanVienfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NhanVienfragment extends Fragment {
    ListView lvNhanVien;
    ArrayList<NhanVien> list;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaNV;
    private TextInputEditText edTenNV,edSDT,edUser,edPass;
    Button btnSaveNV,btnCancelNV;

//    static NhanVienDAO dao;
    private NhanVienDAO dao;
    NhanVienAdapter adapter;
    NhanVien item;

    String regex = "^0{1}[0-9]{9}";
    public NhanVienfragment() {
        // Required empty public constructor
    }


    public static NhanVienfragment newInstance() {
        NhanVienfragment fragment = new NhanVienfragment();
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
        return inflater.inflate(R.layout.fragment_nhanvien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvNhanVien = view.findViewById(R.id.lvNhanVien);
        fab = view.findViewById(R.id.fab);
        dao = new NhanVienDAO(getActivity());
        capNhatLv();
        fab.setOnClickListener(view1 -> {
            openDialog(getActivity());
        });
        lvNhanVien.setOnItemLongClickListener((parent, view1, position, id) -> {
            item = list.get(position);
            openDialog(getActivity());
            return false;
        });
    }
    protected void openDialog(final Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.nhan_vien_dialog);
//        edMaNV = dialog.findViewById(R.id.edMaNV);

        edTenNV = dialog.findViewById(R.id.edTenNV);
        edSDT = dialog.findViewById(R.id.edSDT);
        edUser = dialog.findViewById(R.id.edUser);
        edPass = dialog.findViewById(R.id.edPass);
        btnSaveNV = dialog.findViewById(R.id.btnSaveNV);
        btnCancelNV = dialog.findViewById(R.id.btnCancelNV);
//        edMaNV.setEnabled(false);


//        if (type != 0){
//            edMaNV.setText(String.valueOf(item.maNv));
//            edTenNV.setText(item.tenNhanVien);
//            edSDT.setText(item.sdt);
//            edUser.setText(item.User);
//            edPass.setText(item.matKhau);
//        }
        //Nút [Hủy]
        //Xoá tất cả EditText
        btnCancelNV.setOnClickListener(view -> {
            dialog.dismiss();
        });
        ///Nút [Lưu]
        //Kiểm tra thông tin có nhập đầy đủ không?
        //Kiểm tra mật khẩu và xác nhận có giống
        //nhau hay không?
        //Lưu thông tin tài khoản vào CSDL
        //Thông báo thông tin phù hợp
        btnSaveNV.setOnClickListener(view -> {
            item = new NhanVien();
            item.tenNhanVien = edTenNV.getText().toString();
            item.sdt = edSDT.getText().toString();
            item.maNv = edUser.getText().toString();
            item.matKhau =edPass.getText().toString();
            if (validate()>0){
//                if (type==0){
                    if (dao.insert(item)>0){
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        edTenNV.setText("");
                        edSDT.setText("");
                        edUser.setText("");
                        edPass.setText("");
                    }else {
                        Toast.makeText(context, "Thêm Thất bại", Toast.LENGTH_SHORT).show();
                    }

//                }else {
//                    item.maNv = edMaNV.getText().toString();
//                    if (dao.updatePass(item)>0){
//                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
//                    }else {
//                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
//                    }
//                }
                capNhatLv();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
// cập nhật lại toàn bộ list view khi tác động vào các thực thể
    void capNhatLv() {
        list = (ArrayList<NhanVien>) dao.getAll();
        adapter = new NhanVienAdapter(getActivity(),this,list);
        lvNhanVien.setAdapter(adapter);
    }
    // khi ấn vào xóa thì hiển thị ra dialog xóa
    public void xoa(final String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setIcon(R.drawable.ic_baseline_delete_24);
        builder.setCancelable(true);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // gọi trong dao và xóa theo id rồi cập nhật listview và hiển thị ra màn hình
                dao.delete(Id);
                capNhatLv();
                dialogInterface.cancel();
            }
        });
        // nếu không thì ẩn thanh dialog
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        builder.show();
    }
    // kiểm tra giá trị nhập rỗng và check số điện thoại có trùng với biểu thức chính quy hay không
    public int validate(){
        int check = 1;
        String regexsdt = edSDT.getText().toString();
        if (edTenNV.getText().toString().length()==0 || edSDT.getText().toString().length()==0 ||edUser.getText().toString().length()==0 ||edPass.getText().toString().length()==0){
            Toast.makeText(getContext(), "Bạn Phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        else if (edSDT.getText().toString().length() < 10 || regexsdt.length() > 13 || regexsdt.matches(regex) == false) {
            Toast.makeText(getActivity(), "Bạn phải nhập đúng số điện thoại", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}