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
        //N??t [H???y]
        //???Xo?? t???t c??? EditText
        btnCancelNV.setOnClickListener(view -> {
            dialog.dismiss();
        });
        ///N??t [L??u]
        //???Ki???m tra th??ng tin c?? nh???p ?????y ????? kh??ng?
        //???Ki???m tra m???t kh???u v?? x??c nh???n c?? gi???ng
        //nhau hay kh??ng?
        //???L??u th??ng tin t??i kho???n v??o CSDL
        //???Th??ng b??o th??ng tin ph?? h???p
        btnSaveNV.setOnClickListener(view -> {
            item = new NhanVien();
            item.tenNhanVien = edTenNV.getText().toString();
            item.sdt = edSDT.getText().toString();
            item.maNv = edUser.getText().toString();
            item.matKhau =edPass.getText().toString();
            if (validate()>0){
//                if (type==0){
                    if (dao.insert(item)>0){
                        Toast.makeText(context, "Th??m th??nh c??ng", Toast.LENGTH_SHORT).show();
                        edTenNV.setText("");
                        edSDT.setText("");
                        edUser.setText("");
                        edPass.setText("");
                    }else {
                        Toast.makeText(context, "Th??m Th???t b???i", Toast.LENGTH_SHORT).show();
                    }

//                }else {
//                    item.maNv = edMaNV.getText().toString();
//                    if (dao.updatePass(item)>0){
//                        Toast.makeText(context, "S???a th??nh c??ng", Toast.LENGTH_SHORT).show();
//                    }else {
//                        Toast.makeText(context, "S???a th???t b???i", Toast.LENGTH_SHORT).show();
//                    }
//                }
                capNhatLv();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
// c???p nh???t l???i to??n b??? list view khi t??c ?????ng v??o c??c th???c th???
    void capNhatLv() {
        list = (ArrayList<NhanVien>) dao.getAll();
        adapter = new NhanVienAdapter(getActivity(),this,list);
        lvNhanVien.setAdapter(adapter);
    }
    // khi ???n v??o x??a th?? hi???n th??? ra dialog x??a
    public void xoa(final String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("B???n c?? mu???n x??a kh??ng?");
        builder.setIcon(R.drawable.ic_baseline_delete_24);
        builder.setCancelable(true);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // g???i trong dao v?? x??a theo id r???i c???p nh???t listview v?? hi???n th??? ra m??n h??nh
                dao.delete(Id);
                capNhatLv();
                dialogInterface.cancel();
            }
        });
        // n???u kh??ng th?? ???n thanh dialog
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        builder.show();
    }
    // ki???m tra gi?? tr??? nh???p r???ng v?? check s??? ??i???n tho???i c?? tr??ng v???i bi???u th???c ch??nh quy hay kh??ng
    public int validate(){
        int check = 1;
        String regexsdt = edSDT.getText().toString();
        if (edTenNV.getText().toString().length()==0 || edSDT.getText().toString().length()==0 ||edUser.getText().toString().length()==0 ||edPass.getText().toString().length()==0){
            Toast.makeText(getContext(), "B???n Ph???i nh???p ?????y ????? th??ng tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        else if (edSDT.getText().toString().length() < 10 || regexsdt.length() > 13 || regexsdt.matches(regex) == false) {
            Toast.makeText(getActivity(), "B???n ph???i nh???p ????ng s??? ??i???n tho???i", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}