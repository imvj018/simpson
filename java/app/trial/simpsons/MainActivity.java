package app.trial.simpsons;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 2800;

    Animation tanim, banim;
    ImageView logo;
    ImageView Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tanim = AnimationUtils.loadAnimation(this, R.anim.topanim);
        banim = AnimationUtils.loadAnimation(this, R.anim.botanim);

        logo = findViewById(R.id.imageView4);
        Name = findViewById(R.id.imageView);
        logo.setAnimation(tanim);
        Name.setAnimation(banim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);

                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);


    }
}