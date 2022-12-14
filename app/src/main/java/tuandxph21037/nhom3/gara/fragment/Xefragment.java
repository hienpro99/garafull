package tuandxph21037.nhom3.gara.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import tuandxph21037.nhom3.gara.Adapter.LoaiXeSpinnerAdapter;
import tuandxph21037.nhom3.gara.Adapter.XeAdapter;
import tuandxph21037.nhom3.gara.DAO.HoaDonDAO;
import tuandxph21037.nhom3.gara.DAO.LoaiXeDAO;
import tuandxph21037.nhom3.gara.DAO.XeDAO;
import tuandxph21037.nhom3.gara.Model.LoaiXe;
import tuandxph21037.nhom3.gara.Model.Xe;
import tuandxph21037.nhom3.gara.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Xefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Xefragment extends Fragment {
    ListView lvXe;
    XeDAO xeDAO;
    XeAdapter adapter;
    Xe item;
    List<Xe> list;

    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaXe, edTenXe, edBienSoX,edGiaMua;
    Spinner spinner;
    ImageView imageView;
    Button btnSave, btnCancel,btnChoose;

    LoaiXeSpinnerAdapter spinnerAdapter;
    ArrayList<LoaiXe> listLoaiXe;
    LoaiXeDAO loaiXeDAO;
    LoaiXe loaiXe;
    int maLoaiXe, position;

    String regex = "([0-9]{2}[A-Z]-)([0-9]{4,5})";
    ////
    private static final  int IMAGE_PICK_CODE = 1000;

    private static final  int PERMISSION_CODE = 1001;

    public Xefragment() {
        // Required empty public constructor
    }


    public static Xefragment newInstance() {
        Xefragment fragment = new Xefragment();
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
        return inflater.inflate(R.layout.fragment_xe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvXe = view.findViewById(R.id.lvXe);
        xeDAO = new XeDAO(getActivity());
        capNhatLv();
        loaiXeDAO= new LoaiXeDAO(getActivity());

        fab = view.findViewById(R.id.fab);
        //FloatingButton: m??? h???p tho???i th??m/s???a xe,
        fab.setOnClickListener(view1 -> {
            //ki???m tra n???u lo???i xe = 0 ho???c r???ng c?? ngh??a l?? kh??ng t???n t???i lo???i xe th?? th??ng b??o
            if(loaiXeDAO.getAll().size()==0){
                Toast.makeText(getActivity(), "B???n c???n th??m lo???i xe tr?????c", Toast.LENGTH_SHORT).show();
            }else{
                //n???u t???n t???i th?? hi???n th??? dialog th??m xe
                openDiaLog(getActivity(), 0);
            }
        });
        //???n gi??? ????? c???p nh???t khi type = 1
        lvXe.setOnItemLongClickListener((parent, view1, position, id) -> {
//            item = list.get(position);
//            openDiaLog(getActivity(), 1);
//            return false;
            HoaDonDAO hddao=new HoaDonDAO(getContext());
            item = list.get(position);
            if(hddao.checkXeHD(String.valueOf(item.maXe)).size()==0){
                openDiaLog(getActivity(), 1);
            }else {
                Toast.makeText(getActivity(), "T???n t???i xe trong h??a ????n \n kh??ng th??? s???a", Toast.LENGTH_SHORT).show();
            }
            return false;
        });
    }
    void capNhatLv() {
        //c???p nh???t l???i list theo b???ng xe c?? trong dao theo getALL data
        list = (List<Xe>) xeDAO.getAll();
        adapter = new XeAdapter(getActivity(), this, list);
        lvXe.setAdapter(adapter);
        // g???i adapter ????? chi???u c??c th???c th??? l??n m??n h??nh theo list c?? trong sql
    }
    public void xoa(final String Id) {
        //s??? d???ng alert ????? hi???n th??? thanh th??ng b??o khi ???n Img x??a
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("b???n c?? mu???n x??a kh??ng?");
        builder.setIcon(R.drawable.ic_baseline_delete_24);
        builder.setCancelable(true);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //n???u ch???n yes th?? x??a th???c th??? xe ???? ch???n v?? trong sql d??? li???u c??ng ??c x??a lu??n
                // r???i c???p nh???t th???c th??? b???ng h??m capnhatlv()
                //???n thanh dialog ??i khi ???? th???c hi???n xong ch???c n??ng
                xeDAO.delete(Id);
                capNhatLv();
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // n???u ch???n no th?? ???n thanh dialog ??i
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
        // show builder
    }
    protected void openDiaLog(final Context context, final int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.xe_dialog);
        edMaXe = dialog.findViewById(R.id.edMaXe);
        edTenXe = dialog.findViewById(R.id.edTenXe);
        spinner = dialog.findViewById(R.id.spLoaiXe);

        imageView = dialog.findViewById(R.id.imgXe);

        edBienSoX = dialog.findViewById(R.id.edBienSoX);
        edGiaMua = dialog.findViewById(R.id.edGiaMua);
        btnChoose = dialog.findViewById(R.id.btnChoose);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnSave = dialog.findViewById(R.id.btnSave);
        // l???y t??n lo???i xe theo list lo???i xe c?? trong sql
        listLoaiXe = new ArrayList<LoaiXe>();
        loaiXeDAO = new LoaiXeDAO(context);
        listLoaiXe = (ArrayList<LoaiXe>) loaiXeDAO.getAll();
        spinnerAdapter = new LoaiXeSpinnerAdapter(context, listLoaiXe);
        spinner.setAdapter(spinnerAdapter);
        //r???i g???i adapter ????? chi???u l??n m??n h??nh danh s??ch lo???i xe theo m?? lo???i xe v?? t??n lo???i xe

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // d??ng ????? ch???n lo???i xe ???? hi???n th??? trong spinner b???ng m?? lo???i xe
                maLoaiXe = listLoaiXe.get(position).maLoaiXe;
                Toast.makeText(context, "ch???n " + listLoaiXe.get(position).tenLoai, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // ki???m tra type insert 0 hay update 1
        edMaXe.setEnabled(false);
        if (type != 0) {
            edMaXe.setText(String.valueOf(item.maXe));
            edTenXe.setText(item.tenXe);
            edBienSoX.setText((item.bienSo));
            edGiaMua.setText(String.valueOf(item.gia));
            for (int i = 0; i < listLoaiXe.size(); i++)
                if (item.maLoaiXe == (listLoaiXe.get(i).maLoaiXe)) {
                    position = i;
                }
            Log.i("demo", "posxe" + position);
            spinner.setSelection(position);

        }
        //N??t [Hu???]: ????ng h???p tho???i
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ///// btn ???nh
        //khi click v??o buton th?? hi???n th??? thanh c???p quy???n v??o b??? s??u t???p
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //s??t quy???n ?????c, l???y ???nh trong b??? s??u t???p
                if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                    if (ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        //ch???n ???nh trong b??? s??u t???p
                        pickImageFromGallery();
                    }

                }
                else {
                    //ch???n ???nh trong b??? s??u t???p
                    pickImageFromGallery();
                }
            }
        });
        ////

        ///N??t [L??u]: L??u xe v??o SQLite, ????ng h???p tho???i.
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ki???m tra t??nh nh???p r???ng v?? h???p l???
                if (validate() > 0) {
                    item = new Xe();
                    item.tenXe = edTenXe.getText().toString();
                    item.bienSo = (edBienSoX.getText().toString());
                    item.gia = Integer.parseInt(edGiaMua.getText().toString());
                    item.maLoaiXe = maLoaiXe;
                    imageViewtoByte(imageView);
                    item.img=imageViewtoByte(imageView);
                    if (type == 0) {
                        //n???u type = 0 th?? insert c??c gi?? tr??? v?? l??u v??o sql
                        if (xeDAO.insert(item) > 0) {
                            Toast.makeText(context, "Th??m th??nh c??ng", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Th??m Th???t b???i", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        //n???u type =1 th?? c???p nh???t
                        item.maXe = Integer.parseInt(edMaXe.getText()   .toString());
                        if (xeDAO.update(item) > 0) {
                            Toast.makeText(context, "S???a th??nh c??ng", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "S???a th???t b???i", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatLv();
                    dialog.dismiss();
                    //c???p nh???t l???i list view khi t??c ?????ng v??o th???c th??? th??nh c??ng
                }
            }
        });
        dialog.show();
    }


    //h??m chuy???n ?????i ???nh th??nh m???ng byte
    private byte[] imageViewtoByte(ImageView imageView) {
        Bitmap bitmap =((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    public int validate() {
        XeDAO xedao = new XeDAO(getActivity());
        int check = 1;
        //ki???m tra t??nh nh???p r???ng v?? bi???u th???c ch??nh quy
        String regexBS = edBienSoX.getText().toString();
        if (edTenXe.getText().toString().length() == 0 || edGiaMua.getText().toString().equals("")|| edBienSoX.getText().toString().equals("")){
            Toast.makeText(getContext(), "B???n ph???i nh???p ?????y ????? th??ng tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else if (regexBS.length() > 9||regexBS.matches(regex)== false){
            Toast.makeText(getActivity(), "Sai ?????nh d???ng bi???n s??? xe", Toast.LENGTH_SHORT).show();
            check = -1;
        }else if(xedao.checkBienSo(edBienSoX.getText().toString()).size()==1){
            Toast.makeText(getActivity(), "Bi???n s??? ???? t???n t???i", Toast.LENGTH_SHORT).show();
            edBienSoX.setText("");
            check = -1;
        }
        return check;
    }
    //???nh
    //ch???n ???nh trong b??? s??u t???p
    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                    //n???u c???p quy???n th??nh c??ng th?? v??o b??? s??u t???p
                }
                else {
                    //ko th?? th??ng b??o
                    Toast.makeText(getActivity(), "permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }//h??m y??u c???u quy???n ???ng d???ng

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK){
            imageView.setImageURI(data.getData());

        }
    }// h??m tr??? v??? m???t k???t qu??? khi ???? ch???n ???nh, hi???n th??? ???nh ra m???t image view
}