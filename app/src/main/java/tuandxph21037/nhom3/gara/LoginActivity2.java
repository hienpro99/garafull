package tuandxph21037.nhom3.gara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import tuandxph21037.nhom3.gara.DAO.NhanVienDAO;

public class LoginActivity2 extends AppCompatActivity {
    private EditText edUserName,edPassword;
    private Button btnLogin,btnCancel;
    private CheckBox chkRememberPass;
    private String strUser, strPass;
    NhanVienDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        setContentView(R.layout.activity_login2);
        setTitle("Đăng nhập");
        edUserName = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel);
        chkRememberPass = findViewById(R.id.chkRememberPass);
        dao = new NhanVienDAO(this);
        //Đọc uer pass trong sharedpreferences
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        edUserName.setText(pref.getString("USERNAME",""));
        edPassword.setText(pref.getString("PASSWORD",""));
        chkRememberPass.setChecked(pref.getBoolean("REMEMBER",false));
        //Xoá tên đăng nhập và mật khẩu.
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edUserName.setText("");
                edPassword.setText("");
            }
        });
        //Kiểm tra thông tin đăng nhập của người
        //dùng có tồn tại trong bảng ThuThu hay
        //không? Và đưa ra các thông báo phù hợp.
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });

    }
    private void checkLogin() {
        strUser = edUserName.getText().toString();
        strPass = edPassword.getText().toString();
        if (strUser.isEmpty() || strPass.isEmpty()){
            Toast.makeText(this, "Tên đăng nhập và mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
        }else if (strUser.equals("admin") && strPass.equals("admin")){
                Toast.makeText(this, "Login thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUser,strPass,chkRememberPass.isChecked());
                Intent i = new Intent(getApplicationContext(),ManGiaoDienActivity.class);
                i.putExtra("user",strUser);
                startActivity(i);
                finish();
            }else if (dao.checkLogin(strUser,strPass) > 0){
                Toast.makeText(this, "Login thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUser,strPass,chkRememberPass.isChecked());
                Intent i = new Intent(getApplicationContext(),ManGiaoDienActivity.class);
                i.putExtra("user",strUser);
                startActivity(i);
                finish();
            }else {
                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
        }//end else
    }
///Nếu check thông tin đăng nhập sẽ lưu vào
//SharedPreferences.
    private void rememberUser(String strUser, String strPass, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status){
            //xóa tình trạng lưu trữ trước đó khi đổi mật khẩu
            edit.clear();
        }else {
            //lưu dữ liệu
            edit.putString("USERNAME",strUser);
            edit.putString("PASSWORD",strPass);
            edit.putBoolean("REMEMBER",status);
        }
        //Luwu lai toan bo
        edit.commit();
    }
}