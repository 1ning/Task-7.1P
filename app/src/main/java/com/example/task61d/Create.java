package com.example.task61d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task61d.Database.DatabaseHelper;

public class Create extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    EditText name;
    EditText phone;
    EditText data;
    EditText location;
    EditText description;
    Button Create,found,lost;
    String name1,location1,phone1,data1,description1,type="Lost";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createorder2);
        findId();
        dbHelper = new DatabaseHelper(this, "database2", null, 1);
        found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            type="Found";
            }
        });
        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="Lost";
            }
        });
        Create.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                name1= name.getText().toString();
                phone1=phone.getText().toString();
                data1=data.getText().toString();
                location1=location.getText().toString();
                description1=description.getText().toString();
                if(name1!=null&&phone1!=null&&data1!=null&&location1!=null& description1!=null){
                    db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    putvalue(values);
                    long result = db.insert("orders", null, values);
                    db.close();
                    dbHelper.close();
                    if (result > 0) {
                        Toast.makeText(com.example.task61d.Create.this, "Order created successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(com.example.task61d.Create.this, MainActivity.class);
                        startActivity(intent);
                        finish(); }
                }
                else{
                    Toast.makeText(com.example.task61d.Create.this, "Bad", Toast.LENGTH_SHORT).show();
                }
                 }
            });
    }
      public void findId(){
          name=findViewById(R.id.name);
          phone=findViewById(R.id.phone);
          location=findViewById(R.id.location);
          data=findViewById(R.id.data);
          description=findViewById(R.id.description);
          found=findViewById(R.id.btnfind);
          lost=findViewById(R.id.btnlost);
          Create=findViewById(R.id.Create);
      }

    public void putvalue(ContentValues values){
        values.put("name", name1);
        values.put("phone", phone1);
        values.put("data", data1);
        values.put("location", location1);
        values.put("description", description1);
        values.put("type", type);
    }
}