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
        fab.setOnClickListener(view1 -> {
            if(loaiXeDAO.getAll().size()==0){
                Toast.makeText(getActivity(), "Bạn cần thêm loại xe trước", Toast.LENGTH_SHORT).show();
            }else{
                openDiaLog(getActivity(), 0);
            }
        });
        lvXe.setOnItemLongClickListener((parent, view1, position, id) -> {
            item = list.get(position);
            openDiaLog(getActivity(), 1);
            return false;
        });
    }
    void capNhatLv() {
        list = (List<Xe>) xeDAO.getAll();
        adapter = new XeAdapter(getActivity(), this, list);
        lvXe.setAdapter(adapter);
    }
    public void xoa(final String Id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("bạn có muốn xóa không?");
        builder.setIcon(R.drawable.ic_baseline_delete_24);
        builder.setCancelable(true);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                xeDAO.delete(Id);
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

        listLoaiXe = new ArrayList<LoaiXe>();
        loaiXeDAO = new LoaiXeDAO(context);
        listLoaiXe = (ArrayList<LoaiXe>) loaiXeDAO.getAll();
        spinnerAdapter = new LoaiXeSpinnerAdapter(context, listLoaiXe);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiXe = listLoaiXe.get(position).maLoaiXe;
                Toast.makeText(context, "chọn " + listLoaiXe.get(position).tenLoai, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ///// btn ảnh
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                    if (ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        pickImageFromGallery();
                    }

                }
                else {
                    pickImageFromGallery();
                }
            }
        });
        ////


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate() > 0) {
                    item = new Xe();
                    item.tenXe = edTenXe.getText().toString();
                    item.bienSo = (edBienSoX.getText().toString());
                    item.gia = Integer.parseInt(edGiaMua.getText().toString());
                    item.maLoaiXe = maLoaiXe;
                    imageViewtoByte(imageView);
                    item.img=imageViewtoByte(imageView);
                    if (type == 0) {
                        if (xeDAO.insert(item) > 0) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm Thất bại", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        item.maXe = Integer.parseInt(edMaXe.getText()   .toString());
                        if (xeDAO.update(item) > 0) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatLv();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }



    private byte[] imageViewtoByte(ImageView imageView) {
        Bitmap bitmap =((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    public int validate() {
        int check = 1;
        if (edTenXe.getText().toString().length() == 0 || edGiaMua.getText().toString().equals("")|| edBienSoX.getText().toString().equals("")){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
    //ảnh

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
                }
                else {
                    Toast.makeText(getActivity(), "permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK){
            imageView.setImageURI(data.getData());

        }
    }
}