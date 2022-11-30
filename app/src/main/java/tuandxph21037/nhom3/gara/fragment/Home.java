package tuandxph21037.nhom3.gara.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import tuandxph21037.nhom3.gara.LoginActivity2;
import tuandxph21037.nhom3.gara.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    ViewFlipper viewFlipper;
    LinearLayout idDoiMK,idDangXuat,idThoat;
    public Home() {
        // Required empty public constructor
    }


    public static Home newInstance() {
        Home fragment = new Home();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewFlipper = view.findViewById(R.id.viewlipper);
        ActionViewFlipper();
        idDoiMK = view.findViewById(R.id.idDoiMK);
        idDoiMK.setOnClickListener(view1 -> {
            replaceFragment(DoiMatKhau.newInstance());
        });
        idDangXuat= view.findViewById(R.id.idDangXuat);
        idDangXuat.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), LoginActivity2.class);
            startActivity(intent);
        });
        idThoat = view.findViewById(R.id.idThoat);
        idThoat.setOnClickListener(view1 -> {
            Toast.makeText(getActivity(), "Goodbye!!", Toast.LENGTH_SHORT).show();
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startActivity(startMain);
        });
    }

    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://tintuc.ngan-hang.com/media/blog/Trao-xe-xin_banner-Web-1.png");
        mangquangcao.add("https://nissanthanhhoa.vn/upload_images/images/2022/01/06/banner%207x1%2C5m_%201200_628.png");
        mangquangcao.add("http://suzukisaigon.vn/wp-content/uploads/2020/07/BANNER-ERTIGA-2020-1-1024x464.jpg");
        for (int i =0; i <mangquangcao.size();i++){
            ImageView imageView = new ImageView(getContext());
            Glide.with(getContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(7000);
        viewFlipper.setAutoStart(true);
        Animation sline_in = AnimationUtils.loadAnimation(getContext(),R.anim.sline_in_right);
        Animation sline_out = AnimationUtils.loadAnimation(getContext(),R.anim.sline_out_right);
        viewFlipper.setInAnimation(sline_in);
        viewFlipper.setOutAnimation(sline_out);
    }
    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout,fragment);
        transaction.commit();
    }
}