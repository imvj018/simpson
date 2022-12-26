package app.trial.simpsons;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.provider.Telephony.Mms.Part.TEXT;

public class Login extends AppCompatActivity {
    TextInputLayout regUname, regPword;
    Button Loginbutton;
    TextView time;
    String timeee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        regUname = findViewById(R.id.login_Username);
        regPword = findViewById(R.id.login_Password);
        Loginbutton = findViewById(R.id.login_button);
        time = findViewById(R.id.time);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat stime = new SimpleDateFormat("hh:mm:ss a");
        timeee = stime.format(calendar.getTime());
        time.setText(timeee);


        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = regUname.getEditText().getText().toString();
                String password = regPword.getEditText().getText().toString();
                String logintime = timeee;
                SharedPreferences sharedPreferences = getSharedPreferences("KEY", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    editor.putString(TEXT, username);
                }
                editor.apply();

                if (username.equals("picker1") && password.equals("pw1") ||
                        username.equals("picker2") && password.equals("pw2") ||
                        username.equals("picker3") && password.equals("pw3")) {

                    Intent intent = new Intent(Login.this, dashboard.class);
                    intent.putExtra("USERNAME", username);
                    intent.putExtra("LOGTIME", logintime);

                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(Login.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                }

            }


        });


    }
    public void onBackPressed(){
        finishAffinity();
    }
}