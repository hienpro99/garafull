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
        //FloatingButton: mở hộp thoại thêm/sửa xe,
        fab.setOnClickListener(view1 -> {
            //kiểm tra nếu loại xe = 0 hoặc rỗng có nghĩa là không tồn tại loại xe thì thông báo
            if(loaiXeDAO.getAll().size()==0){
                Toast.makeText(getActivity(), "Bạn cần thêm loại xe trước", Toast.LENGTH_SHORT).show();
            }else{
                //nếu tồn tại thì hiển thị dialog thêm xe
                openDiaLog(getActivity(), 0);
            }
        });
        //ấn giữ để cập nhật khi type = 1
        lvXe.setOnItemLongClickListener((parent, view1, position, id) -> {
//            item = list.get(position);
//            openDiaLog(getActivity(), 1);
//            return false;
            HoaDonDAO hddao=new HoaDonDAO(getContext());
            item = list.get(position);
            if(hddao.checkXeHD(String.valueOf(item.maXe)).size()==0){
                openDiaLog(getActivity(), 1);
            }else {
                Toast.makeText(getActivity(), "Tồn tại xe trong hóa đơn \n không thể sửa", Toast.LENGTH_SHORT).show();
            }
            return false;
        });
    }
    void capNhatLv() {
        //cập nhật lại list theo bảng xe có trong dao theo getALL data
        list = (List<Xe>) xeDAO.getAll();
        adapter = new XeAdapter(getActivity(), this, list);
        lvXe.setAdapter(adapter);
        // gọi adapter để chiếu các thực thể lên màn hình theo list có trong sql
    }
    public void xoa(final String Id) {
        //sử dụng alert để hiển thị thanh thông báo khi ấn Img xóa
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("bạn có muốn xóa không?");
        builder.setIcon(R.drawable.ic_baseline_delete_24);
        builder.setCancelable(true);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //nếu chọn yes thì xóa thực thể xe đã chọn và trong sql dữ liệu cũng đc xóa luôn
                // rồi cập nhật thực thể bằng hàm capnhatlv()
                //ẩn thanh dialog đi khi đã thực hiện xong chức năng
                xeDAO.delete(Id);
                capNhatLv();
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // nếu chọn no thì ẩn thanh dialog đi
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
        // lấy tên loại xe theo list loại xe có trong sql
        listLoaiXe = new ArrayList<LoaiXe>();
        loaiXeDAO = new LoaiXeDAO(context);
        listLoaiXe = (ArrayList<LoaiXe>) loaiXeDAO.getAll();
        spinnerAdapter = new LoaiXeSpinnerAdapter(context, listLoaiXe);
        spinner.setAdapter(spinnerAdapter);
        //rồi gọi adapter để chiếu lên màn hình danh sách loại xe theo mã loại xe và tên loại xe

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // dùng để chọn loại xe đã hiển thị trong spinner bằng mã loại xe
                maLoaiXe = listLoaiXe.get(position).maLoaiXe;
                Toast.makeText(context, "chọn " + listLoaiXe.get(position).tenLoai, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // kiểm tra type insert 0 hay update 1
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
        //Nút [Huỷ]: đóng hộp thoại
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ///// btn ảnh
        //khi click vào buton thì hiển thị thanh cấp quyền vào bộ sưu tập
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sét quyền đọc, lấy ảnh trong bộ sưu tập
                if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                    if (ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        //chọn ảnh trong bộ sưu tập
                        pickImageFromGallery();
                    }

                }
                else {
                    //chọn ảnh trong bộ sưu tập
                    pickImageFromGallery();
                }
            }
        });
        ////

        ///Nút [Lưu]: Lưu xe vào SQLite, đóng hộp thoại.
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //kiểm tra tính nhập rỗng và hợp lệ
                if (validate() > 0) {
                    item = new Xe();
                    item.tenXe = edTenXe.getText().toString();
                    item.bienSo = (edBienSoX.getText().toString());
                    item.gia = Integer.parseInt(edGiaMua.getText().toString());
                    item.maLoaiXe = maLoaiXe;
                    imageViewtoByte(imageView);
                    item.img=imageViewtoByte(imageView);
                    if (type == 0) {
                        //nếu type = 0 thì insert các giá trị và lưu vào sql
                        if (xeDAO.insert(item) > 0) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm Thất bại", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        //nếu type =1 thì cập nhật
                        item.maXe = Integer.parseInt(edMaXe.getText()   .toString());
                        if (xeDAO.update(item) > 0) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatLv();
                    dialog.dismiss();
                    //cập nhật lại list view khi tác động vào thực thể thành công
                }
            }
        });
        dialog.show();
    }


    //hàm chuyển đổi ảnh thành mảng byte
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
        //kiểm tra tính nhập rỗng và biểu thức chính quy
        String regexBS = edBienSoX.getText().toString();
        if (edTenXe.getText().toString().length() == 0 || edGiaMua.getText().toString().equals("")|| edBienSoX.getText().toString().equals("")){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }else if (regexBS.length() > 9||regexBS.matches(regex)== false){
            Toast.makeText(getActivity(), "Sai định dạng biển số xe", Toast.LENGTH_SHORT).show();
            check = -1;
        }else if(xedao.checkBienSo(edBienSoX.getText().toString()).size()==1){
            Toast.makeText(getActivity(), "Biển số đã tồn tại", Toast.LENGTH_SHORT).show();
            edBienSoX.setText("");
            check = -1;
        }
        return check;
    }
    //ảnh
    //chọn ảnh trong bộ sưu tập
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
                    //nếu cấp quyền thành công thì vào bộ sưu tập
                }
                else {
                    //ko thì thông báo
                    Toast.makeText(getActivity(), "permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }//hàm yêu cầu quyền ứng dụng

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK){
            imageView.setImageURI(data.getData());

        }
    }// hàm trả về một kết quả khi đã chọn ảnh, hiển thị ảnh ra một image view
}