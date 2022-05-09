package com.example.task61d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.task61d.Database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    Button Create;
    Button Show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Create=(Button)findViewById(R.id.Create);
        Show=(Button)findViewById(R.id.Show);
        Create.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
                    Intent intent=new Intent(MainActivity.this,Create.class);
                    startActivity(intent);
                    finish();
                     }
                   });
        Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(MainActivity.this, Order.class);
                startActivity(intent2);
            }
        });

    }
}
