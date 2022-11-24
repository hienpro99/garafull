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
import tuandxph21037.nhom3.gara.fragment.DoiMatKhau;
import tuandxph21037.nhom3.gara.fragment.HoaDonfragment;
import tuandxph21037.nhom3.gara.fragment.Home;
import tuandxph21037.nhom3.gara.fragment.KhachHangfragment;
import tuandxph21037.nhom3.gara.fragment.LoaiXefragment;
import tuandxph21037.nhom3.gara.fragment.NhanVienfragment;
import tuandxph21037.nhom3.gara.fragment.TopNhanVien;
import tuandxph21037.nhom3.gara.fragment.TopXe;
import tuandxph21037.nhom3.gara.fragment.Xefragment;

public class ManGiaoDienActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    FrameLayout frameLayout;
    private NavigationView navigationView;
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
        if(user.equalsIgnoreCase("admin")) {
            navigationView.getMenu().findItem(R.id.nav_qlnhanvien).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_topnhanvien).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_qlkhachhang).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_hoadon).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_doanhsonhanvien).setVisible(true);
            ///
//            navigationView.getMenu().findItem(R.id.nav_home).setVisible(false);

        }else if (user.equalsIgnoreCase("nhanvien")){
            navigationView.getMenu().findItem(R.id.nav_qlkhachhang).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_hoadon).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_doanhsonhanvien).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_qlnhanvien).setVisible(false);
            ////
//            navigationView.getMenu().findItem(R.id.nav_home).setVisible(false);
        }else {
//            navigationView.getMenu().findItem(R.id.nav_themhangxe).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_themloaixe).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_hoadon).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_themxe).setVisible(false);
            // mở màn hình hiển thị danh sách xe
            //
//            replaceFragment(Home.newInstance());

        }
        View view = navigationView.getHeaderView(0);
        TextView nameuser =view.findViewById(R.id.login_nameuser);
        nameuser.setText("Xin chào : "+ user );
        ///home
//        TextView nameuser1 =view.findViewById(R.id.textUserName);
//        nameuser1.setText("Hello: "+ user);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if(id == R.id.nav_home){
            //màn home
            replaceFragment(Home.newInstance());
        }else if (id == R.id.nav_qlnhanvien){
            // màn hình nhân viên
            replaceFragment(NhanVienfragment.newInstance());
        }else if (id == R.id.nav_qlkhachhang){
            // màn hình khách hàng
            replaceFragment(KhachHangfragment.newInstance());
        }else if (id == R.id.nav_hoadon){
            // màn hìnhhóa đơn
            replaceFragment(HoaDonfragment.newInstance());
        }else if (id == R.id.nav_themloaixe){
            // màn hình loại xe
            replaceFragment(LoaiXefragment.newInstance());

//        }else if (id == R.id.nav_themhangxe){
//            // màn hình hãng xe
//            replaceFragment(HangXe.newInstance());

        }else if (id == R.id.nav_themxe){
            // màn hình xe
            replaceFragment(Xefragment.newInstance());

        }else if (id == R.id.nav_topnhanvien){
            // màn hình top nhân viên
            replaceFragment(TopNhanVien.newInstance());

        }else if (id == R.id.nav_topxe){
            // màn hình top xe bán chạy
            replaceFragment(TopXe.newInstance());

        }else if (id == R.id.nav_doanhsonhanvien){
            // màn hình doanh số  nhân viên
            replaceFragment(DoanhSoNhanVien.newInstance());

        }else if (id == R.id.nav_doimatkhau){
            // màn hình đổi mâtj khẩu
            replaceFragment(DoiMatKhau.newInstance());

        }else if (id == R.id.nav_dangxuat){
            // màn hình đăng nhập
            Toast.makeText(this, "Bạn chọn đăng xuất", Toast.LENGTH_SHORT).show();
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
        transaction.commit();
    }
}