package tuandxph21037.nhom3.gara;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import tuandxph21037.nhom3.gara.fragment.DoanhSoNhanVien;
import tuandxph21037.nhom3.gara.fragment.DoanhSoTungNv;
import tuandxph21037.nhom3.gara.fragment.DoiMatKhau;
import tuandxph21037.nhom3.gara.fragment.HoaDonfragment;
import tuandxph21037.nhom3.gara.fragment.Home;
import tuandxph21037.nhom3.gara.fragment.KhachHangfragment;
import tuandxph21037.nhom3.gara.fragment.LoaiXefragment;
import tuandxph21037.nhom3.gara.fragment.NhanVienfragment;
import tuandxph21037.nhom3.gara.fragment.TopNhanVienFragment;
import tuandxph21037.nhom3.gara.fragment.TopXe;
import tuandxph21037.nhom3.gara.fragment.Xefragment;

public class ManGiaoDienActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    FrameLayout frameLayout;
    private NavigationView navigationView;
    public String tennv = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_giao_dien);
        replaceFragment(Home.newInstance());
        drawerLayout = findViewById(R.id.drawer_layout);
        frameLayout = findViewById(R.id.frame_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this, drawerLayout,toolbar, 0,0);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        tennv=user;
        if(user.equalsIgnoreCase("admin")) {
            navigationView.getMenu().findItem(R.id.nav_qlnhanvien).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_topnhanvien).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_qlkhachhang).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_hoadon).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_doanhsonhanvien).setVisible(true);
            ///
//            navigationView.getMenu().findItem(R.id.nav_home).setVisible(false);

        }else {
//        else if (user.equalsIgnoreCase("nhanvien")){
            navigationView.getMenu().findItem(R.id.nav_qlkhachhang).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_hoadon).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_doanhsonhanvien).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_qlnhanvien).setVisible(false);
            ////
//            navigationView.getMenu().findItem(R.id.nav_home).setVisible(false);
        }

        View view = navigationView.getHeaderView(0);
        TextView nameuser =view.findViewById(R.id.login_nameuser);
        nameuser.setText("Xin ch??o : "+ user );
        ///home
//        TextView nameuser1 =view.findViewById(R.id.textUserName);
//        nameuser1.setText("Hello: "+ user);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if(id == R.id.nav_home){
            //m??n home
            replaceFragment(Home.newInstance());
        }else if (id == R.id.nav_qlnhanvien){
            // m??n h??nh nh??n vi??n
            replaceFragment(NhanVienfragment.newInstance());
        }else if (id == R.id.nav_qlkhachhang){
            // m??n h??nh kh??ch h??ng
            replaceFragment(KhachHangfragment.newInstance());
        }else if (id == R.id.nav_hoadon){
            // m??n h??nhh??a ????n
            replaceFragment(HoaDonfragment.newInstance());
        }else if (id == R.id.nav_themloaixe){
            // m??n h??nh lo???i xe
            replaceFragment(LoaiXefragment.newInstance());

//        }else if (id == R.id.nav_themhangxe){
//            // m??n h??nh h??ng xe
//            replaceFragment(HangXe.newInstance());

        }else if (id == R.id.nav_themxe){
            // m??n h??nh xe
            replaceFragment(Xefragment.newInstance());

        }else if (id == R.id.nav_topnhanvien){
            // m??n h??nh top nh??n vi??n
            replaceFragment(TopNhanVienFragment.newInstance());

        }else if (id == R.id.nav_topxe){
            // m??n h??nh top xe b??n ch???y
            replaceFragment(TopXe.newInstance());

        }else if (id == R.id.nav_doanhsonhanvien){
            Toast.makeText(this, tennv, Toast.LENGTH_SHORT).show();
            if(tennv.equals("admin")){
                replaceFragment(DoanhSoNhanVien.newInstance());

            }else {
                replaceFragment(DoanhSoTungNv.newInstance());
            };
            // m??n h??nh doanh s???  nh??n vi??n

        }else if (id == R.id.nav_doimatkhau){
            // m??n h??nh ?????i m??tj kh???u
            replaceFragment(DoiMatKhau.newInstance());

        }else if (id == R.id.nav_dangxuat){
            // m??n h??nh ????ng nh???p
            Toast.makeText(this, "B???n ch???n ????ng xu???t", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ManGiaoDienActivity.this,LoginActivity2.class);
            startActivity(intent);
            finish();

        }else if (id == R.id.nav_thoat){
//            System.exit(0);
            Toast.makeText(this, "Goodbye!!", Toast.LENGTH_SHORT).show();
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startActivity(startMain);
            finish();
        }
        drawerLayout.closeDrawer(navigationView);
        return true;
    }
    @Override
    public void onBackPressed() {
        if ( drawerLayout.isDrawerOpen(navigationView)){
            drawerLayout.closeDrawer(navigationView);
        }else {
            super.onBackPressed();
        }

    }
    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout,fragment);
        transaction.addToBackStack(Home.newInstance().getClass().getSimpleName());
        transaction.commit();
    }
    public String getTennv() {
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        tennv=user;
        return user;
    }
}