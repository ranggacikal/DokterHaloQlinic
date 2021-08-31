package id.luvie.luviedokter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import id.luvie.luviedokter.databinding.ActivityKebijakanPrivasiBinding;

public class KebijakanPrivasiActivity extends AppCompatActivity {

    ActivityKebijakanPrivasiBinding binding;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKebijakanPrivasiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        url = getIntent().getStringExtra("url");

        if (url!=null){

            binding.wbKebijakan.loadUrl(url);

        }else{
            Toast.makeText(this, "URL KOSONG", Toast.LENGTH_SHORT).show();
        }

        PushDownAnim.setPushDownAnimTo(binding.imgBackKebijakanPrivasi)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

    }
}