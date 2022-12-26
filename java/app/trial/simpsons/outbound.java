package app.trial.simpsons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import app.trial.simpsons.ob1.active1.activeoto;

public class outbound extends AppCompatActivity {
    Button queue, pending, completed, active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outbound);
        queue = findViewById(R.id.button41);
        pending = findViewById(R.id.button51);
        completed = findViewById(R.id.button31);
        active = findViewById(R.id.button21);
        queue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(outbound.this, queueoto.class);
                startActivity(intent);
            }
        });

        active.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(outbound.this, activeoto.class);
                startActivity(intent);
            }
        });


        pending.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(outbound.this, pendingoto.class);
                startActivity(intent);
            }
        });

        completed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(outbound.this, completedoto.class);
                startActivity(intent);
            }
        });


    }
    public void onBackPressed(){
        Intent inten =  new Intent(this, dashboard.class);
        startActivity(inten);
    }
}