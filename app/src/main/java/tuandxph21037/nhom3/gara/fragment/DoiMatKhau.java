package tuandxph21037.nhom3.gara.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import tuandxph21037.nhom3.gara.DAO.NhanVienDAO;
import tuandxph21037.nhom3.gara.Model.NhanVien;
import tuandxph21037.nhom3.gara.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoiMatKhau#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoiMatKhau extends Fragment {
    private TextInputEditText edPassOld,edPass,edRePass;
    private Button btnSave,btnCancel;
    private NhanVienDAO dao;
    public DoiMatKhau() {
        // Required empty public constructor
    }
    public static DoiMatKhau newInstance() {
        DoiMatKhau fragment = new DoiMatKhau();
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
        return inflater.inflate(R.layout.fragment_doimatkhau, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edPassOld = view.findViewById(R.id.edPassOld);
        edPass = view.findViewById(R.id.edPassChange);
        edRePass = view.findViewById(R.id.edRePassChange);
        btnSave = view.findViewById(R.id.btnSaveUserChange);
        btnCancel = view.findViewById(R.id.btnCancelUserChange);
        dao = new NhanVienDAO(getActivity());
        //nút hủy Xoá tất cả EditText
        btnCancel.setOnClickListener(view1 -> {
            edPassOld.setText("");
            edPass.setText("");
            edRePass.setText("");
        });
        ///nút lưu
        //Kiểm tra thông tin đăng nhập của người
        //dùng có giống với thông tin của người
        //đăng nhập hiện tại hay không?
        //Kiểm tra mật khẩu mới và xác nhận có
        //giống nhau hay không?
        //Thay đổi mật khẩu và cập nhật vào CSDL
        //Thông báo thông tin phù hợp
        btnSave.setOnClickListener(view1 -> {
            //doc user va password trong sharedprerences
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String user = pref.getString("USERNAME","");
            if (validate()>0){
                NhanVien nhanVien = dao.getID(user);
                nhanVien.matKhau = edPass.getText().toString();
                dao.updatePass(nhanVien);
                if (dao.updatePass(nhanVien) > 0){
                    //nếu các giá trị > 0 thì thông báo thay đổi thành công và cập nhật vào shareferences
                    Toast.makeText(getActivity(), "Thay Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    edPassOld.setText("");
                    edPass.setText("");
                    edRePass.setText("");
                }else {
                    //nếu các giá trị bằng 0 thì thông báo
                    Toast.makeText(getActivity(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int validate() {
        int check = 1;
        //xác minh tính nhập rỗng
        //nếu các giá trị bằng 0 thì thông báo
        if (edPassOld.getText().length() == 0 || edPass.getText().length() == 0 || edRePass.getText().length() == 0){
            Toast.makeText(getContext(), "Bạn Phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;

        }else {
            //nếu trong sharedpreferences ko tồn tại mật khẩu cũ hoặc điền sai mật khẩu thì thông báo
            SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld = pref.getString("PASSWORD","");
            String pass = edPass.getText().toString();
            String rePass = edRePass.getText().toString();
            if (!passOld.equals(edPassOld.getText().toString())){
                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(rePass)){
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }

        }
        return check;
    }
}