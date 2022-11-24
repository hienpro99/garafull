package tuandxph21037.nhom3.gara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    TextInputEditText edtendn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtendn = findViewById(R.id.edtendn);
        findViewById(R.id.next).setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this,ManGiaoDienActivity.class);
            intent.putExtra("user",edtendn.getText().toString());
            startActivity(intent);

        });

    }
}