package app.trial.simpsons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import app.trial.simpsons.ib1.active.activeito;


public class inbound extends AppCompatActivity {
    Button queue, pending, completed, active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbound);
        queue = findViewById(R.id.button4);
        pending = findViewById(R.id.button5);
        completed = findViewById(R.id.button3);
        active = findViewById(R.id.button2);
        queue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inbound.this, queueito.class);
                startActivity(intent);
            }
        });

        active.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inbound.this, activeito.class);
                startActivity(intent);
            }
        });


        pending.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inbound.this, pendingito.class);
                startActivity(intent);
            }
        });

        completed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inbound.this, completedito.class);
                startActivity(intent);
            }
        });


    }
    public void onBackPressed(){
        Intent inten =  new Intent(this, dashboard.class);
        startActivity(inten);
    }
}