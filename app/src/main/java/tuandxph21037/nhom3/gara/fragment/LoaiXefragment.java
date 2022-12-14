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
        //FloatingButton: m??? h???p tho???i th??m/s???a lo???i xe,
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
        //N??t [Hu???]: ????ng h???p tho???i
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //N??t [L??u]: L??u lo???i xe v??o SQLite, ????ng h???p
        //tho???i.
        btnSave.setOnClickListener(view -> {
            item = new LoaiXe();
            item.tenLoai = edTenLoaiXe.getText().toString();
            //ki???m tra t??nh nh???p ????ng v?? type =0 hay =1
            if (validate()>0){
                if (type==0){
                    //n???u gi?? tr??? b???ng 0 th?? insert lo???i xe v?? l??u v??o sql trong b???ng lo???i xe
                    if (dao.insert(item)>0){
                        Toast.makeText(context, "Th??m th??nh c??ng", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Th??m Th???t b???i", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    //n???u type = 1 th?? c???p nh???t
                    item.maLoaiXe = Integer.parseInt(edMaLoaiXe.getText().toString());
                    if (dao.update(item)>0){
                        Toast.makeText(context, "S???a th??nh c??ng", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "S???a th???t b???i", Toast.LENGTH_SHORT).show();
                    }
                }
                capNhatLv();
                dialog.dismiss();
                // c???p nh???t l???i list view khi c?? t??c ?????ng th??m s???a x??a v??o lo???i xe
            }
        });
        dialog.show();
        // hi???n th??? l??n m??n h??nh
    }
    //c???p nh???t l???i list theo b???ng lo???i xe c?? trong dao theo getALL data
    void capNhatLv() {
        list = (ArrayList<LoaiXe>) dao.getAll();
        adapter = new LoaiXeAdapter(getActivity(),this,list);
        lv.setAdapter(adapter);
    }
    public void xoa(final String Id){
        // d??ng alert ????? hi???n th??? h???p tho???i khi nh???n v??o Image x??a
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("B???n c?? mu???n x??a kh??ng?");
        builder.setIcon(R.drawable.ic_baseline_delete_24);
        builder.setCancelable(true);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //n???u yes th?? x??a r???i c???p nh???t l???i lv, diaglog b??? ???n ??i khi ho??n th??nh
                dao.delete(Id);
                capNhatLv();
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                // n???u kh??ng x??a th?? ???n dialog ??i
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }
    public int validate(){
        // check t??nh nh???p r???ng r???i th??ng b??o
        int check = 1;
        if (edTenLoaiXe.getText().length()==0){
            Toast.makeText(getContext(), "B???n Ph???i nh???p ?????y ????? th??ng tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

}