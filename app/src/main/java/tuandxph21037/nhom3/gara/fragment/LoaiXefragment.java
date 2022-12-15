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

import tuandxph21037.nhom3.gara.Adapter.LoaiXeAdapter;
import tuandxph21037.nhom3.gara.DAO.LoaiXeDAO;
import tuandxph21037.nhom3.gara.Model.LoaiXe;
import tuandxph21037.nhom3.gara.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoaiXefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoaiXefragment extends Fragment {
    ListView lv;
    ArrayList<LoaiXe> list;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaLoaiXe,edTenLoaiXe;
    Button btnSave,btnCancel;
    static LoaiXeDAO dao;
    LoaiXeAdapter adapter;
    LoaiXe item;
    public LoaiXefragment() {
        // Required empty public constructor
    }


    public static LoaiXefragment newInstance() {
        LoaiXefragment fragment = new LoaiXefragment();
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
        return inflater.inflate(R.layout.fragment_loaixe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.lvLoaiXe);
        fab = view.findViewById(R.id.fab);
        dao = new LoaiXeDAO(getActivity());
        capNhatLv();
        //FloatingButton: mở hộp thoại thêm/sửa loại xe,
        fab.setOnClickListener(view1 -> {
            opendialog(getActivity(),0);

        });
        lv.setOnItemLongClickListener((parent, view1, position, id) -> {
            item = list.get(position);
            opendialog(getActivity(),1);
            return false;
        });
    }
    protected void opendialog(final Context context, final int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.loai_xe_dialog);
        edMaLoaiXe = dialog.findViewById(R.id.edMaLoaiXe);
        edTenLoaiXe = dialog.findViewById(R.id.edTenLoaiXe);
        btnSave = dialog.findViewById(R.id.btnSave);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        edMaLoaiXe.setEnabled(false);
        if (type != 0){
            edMaLoaiXe.setText(String.valueOf(item.maLoaiXe));
            edTenLoaiXe.setText(item.tenLoai);
        }
        //Nút [Huỷ]: đóng hộp thoại
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //Nút [Lưu]: Lưu loại xe vào SQLite, đóng hộp
        //thoại.
        btnSave.setOnClickListener(view -> {
            item = new LoaiXe();
            item.tenLoai = edTenLoaiXe.getText().toString();
            //kiểm tra tính nhập đúng và type =0 hay =1
            if (validate()>0){
                if (type==0){
                    //nếu giá trị bằng 0 thì insert loại xe và lưu vào sql trong bảng loại xe
                    if (dao.insert(item)>0){
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Thêm Thất bại", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    //nếu type = 1 thì cập nhật
                    item.maLoaiXe = Integer.parseInt(edMaLoaiXe.getText().toString());
                    if (dao.update(item)>0){
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                capNhatLv();
                dialog.dismiss();
                // cập nhật lại list view khi có tác động thêm sửa xóa vào loại xe
            }
        });
        dialog.show();
        // hiển thị lên màn hình
    }
    //cập nhật lại list theo bảng loại xe có trong dao theo getALL data
    void capNhatLv() {
        list = (ArrayList<LoaiXe>) dao.getAll();
        adapter = new LoaiXeAdapter(getActivity(),this,list);
        lv.setAdapter(adapter);
    }
    public void xoa(final String Id){
        // dùng alert để hiển thị hộp thoại khi nhấn vào Image xóa
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setIcon(R.drawable.ic_baseline_delete_24);
        builder.setCancelable(true);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //nếu yes thì xóa rồi cập nhật lại lv, diaglog bị ẩn đi khi hoàn thành
                dao.delete(Id);
                capNhatLv();
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                // nếu không xóa thì ẩn dialog đi
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }
    public int validate(){
        // check tính nhập rỗng rồi thông báo
        int check = 1;
        if (edTenLoaiXe.getText().length()==0){
            Toast.makeText(getContext(), "Bạn Phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

}