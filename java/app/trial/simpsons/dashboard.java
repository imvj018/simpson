package app.trial.simpsons;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class dashboard extends AppCompatActivity {
    TextView username, logintime;
    Button inbound11, outbound, timer;
    ImageButton logout;
    AlertDialog.Builder cnfrm;
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        inbound11 = findViewById(R.id.button);
        outbound = findViewById(R.id.button1);
        username = findViewById(R.id.textView);
        logintime = findViewById(R.id.textView1);
        logout = findViewById(R.id.button8);
        cnfrm = new AlertDialog.Builder(this);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("USERNAME");
        String loginTime = intent.getStringExtra("LOGTIME");
        username.setText("Hello " + userName + "!");
        logintime.setText("Logged on " + loginTime);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat(" %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        inbound11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, inbound.class);
                startActivity(intent);
            }
        });

        outbound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, app.trial.simpsons.outbound.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                cnfrm.setMessage("Do you want to Logout?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (running) {
                                    chronometer.stop();
                                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                                    running = false;
                                    int hours = (int) ((pauseOffset / 1000) / (60 * 60));
                                    int minutes = (int) ((pauseOffset / 1000) / (60)) % 60;
                                    int seconds = (int) ((pauseOffset / 1000) % 60);
                                    String wtime = hours + " : " + minutes + " : " + seconds;
                                    Toast.makeText(dashboard.this, "Worked for " + wtime + " hours!", Toast.LENGTH_SHORT).show();

                                }

                                Intent intent = new Intent(dashboard.this, Login.class);
                                startActivity(intent);
                            }
                        }).
                        setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = cnfrm.create();
                alert.setTitle("Logout");
                alert.show();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chronometer.setVisibility(View.VISIBLE);

                if (!running) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    running = true;
                }
            }
        }, 100);

    }

    public void onBackPressed() {
        finishAffinity();
    }
}