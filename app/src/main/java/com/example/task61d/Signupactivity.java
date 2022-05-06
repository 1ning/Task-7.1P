package com.example.task61d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.task61d.Database.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;

public class Signupactivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    EditText Username;
    EditText Password;
    EditText Fullname;
    private Bitmap head;
    EditText Phonenumber;
    EditText ConfirmPassword;
    Button Create;
    ImageView iv_photo;
    FloatingActionButton Addimage;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        findid();;
        dbHelper = new DatabaseHelper(this, "database", null, 1);
        byte[] photo1=getBytes(iv_photo.getBackground());
        Create.setOnClickListener(new View.OnClickListener() {
            String username;
            String password2;
            String fullname;
            String phonenumber;
            String password;
            byte[] photo;
            public void onClick(View view) {
                username = Username.getText().toString().trim();
                password = Password.getText().toString().trim();
                password2 = ConfirmPassword.getText().toString().trim();
                fullname = Fullname.getText().toString().trim();
                phonenumber = Phonenumber.getText().toString().trim();
                photo = getBytes(iv_photo.getDrawable());
                if (username.length()<5) {
                    Toast.makeText(Signupactivity.this, "Username not long enough, please re-enter", Toast.LENGTH_SHORT).show();
                } else if (photo == photo1) {
                    Toast.makeText(Signupactivity.this, "photo cannot be empty, please re-upload", Toast.LENGTH_SHORT).show();
                } else if (password.length()<8) {
                    Toast.makeText(Signupactivity.this, "Password not long enough, please re-enter", Toast.LENGTH_SHORT).show();
                } else if (password2 == null) {
                    Toast.makeText(Signupactivity.this, "ConfirmPassword cannot be empty, please re-enter", Toast.LENGTH_SHORT).show();
                } else if (!password2.equals(password)) {
                    Toast.makeText(Signupactivity.this, "ConfirmPassword is not equal to Password", Toast.LENGTH_SHORT).show();
                } else if (fullname.length()<4) {
                    Toast.makeText(Signupactivity.this, "Full name not long enough, please re-enter", Toast.LENGTH_SHORT).show();
                }
                else {
                    db = dbHelper.getWritableDatabase();
                    //创建游标
                    ContentValues values = new ContentValues();
                    values.put("username", username);
                    values.put("password", password);
                    values.put("phonenumber", phonenumber);
                    values.put("fullname", fullname);
                    photo= imagemTratada(photo);
                    values.put("photo", photo);
                    long result = db.insert("user", null, values);
                    db.close();
                    dbHelper.close();
                    if (result > 0) {
                        Toast.makeText(Signupactivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Signupactivity.this, MainActivity.class);   //Intent intent=new Intent(MainActivity.this,JumpToActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        Addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType( "image/*");
                startActivityForResult( intent1, 0);
            }
        });

    }



    public static byte[] getBytes(Drawable drawable) {
        //将Drawable对象转换为bitmap对象
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        //创建输出字节流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //压缩
        bitmap.compress(Bitmap.CompressFormat.PNG, 1, bos);
        return bos.toByteArray();
    }
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode ==0) {
            iv_photo.setImageURI(data.getData());
        }
    }
    public void findid(){
        Username = (EditText) findViewById(R.id.Username);
        Fullname = (EditText) findViewById(R.id.Fullname);
        Password = (EditText) findViewById(R.id.Password);
        iv_photo=(ImageView) findViewById(R.id.iv_photo);
        Phonenumber = (EditText) findViewById(R.id.Phonenumber);
        ConfirmPassword = (EditText) findViewById(R.id.Password2);
        Create = (Button) findViewById(R.id.Create);
        Addimage = (FloatingActionButton) findViewById(R.id.Addimage);
    }
    private byte[] imagemTratada(byte[] imagem_img){

        while (imagem_img.length > 500000){
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagem_img, 0, imagem_img.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagem_img = stream.toByteArray();
        }
        return imagem_img;

    }

}

