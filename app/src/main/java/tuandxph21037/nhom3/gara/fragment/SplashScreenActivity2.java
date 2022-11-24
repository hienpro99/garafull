package tuandxph21037.nhom3.gara.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import tuandxph21037.nhom3.gara.LoginActivity2;
import tuandxph21037.nhom3.gara.MainActivity;
import tuandxph21037.nhom3.gara.R;

public class SplashScreenActivity2 extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);
                new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginActivity2.class));
                finish();
            }
        },2500);

                //////
        lottieAnimationView = findViewById(R.id.animationView);
        textView2 = findViewById(R.id.textView2);
        textView2.animate().translationY(-1000).setDuration(2700).setStartDelay(0);
//        lottieAnimationView.animate().translationX(2000).setDuration(2000).setStartDelay(2900);

    }
}