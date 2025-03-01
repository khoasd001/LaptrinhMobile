package com.example.hethongquanly;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    //Bước 1: Khai báo các biến
    TextInputEditText editTextTaiKhoan, editTextMatKhau;
    CheckBox checkBoxLuu;
    Button buttonDangNhap;
    //Bước 4 Khai báo tên tập tin
    String perferName = "DangNhapData";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Bước 3: Gọi hàm
        AddView();

        AddEvents();
    }
    //Bước 2 Truy Xuất các view
    private void AddView() {
        editTextTaiKhoan = findViewById(R.id.editTextTaiKhoan);
        editTextMatKhau = findViewById(R.id.editTextMatKhau);
        checkBoxLuu = findViewById(R.id.checkBoxLuu);
        buttonDangNhap = findViewById(R.id.buttonDangNhap);
    }
    //Bước 5 Viết hàm lưu trạng thái đăng nhập
    private void SavePreferences() {
        SharedPreferences preferences = getSharedPreferences(perferName, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if (checkBoxLuu.isChecked()) {
            editor.putString("TaiKhoan", editTextTaiKhoan.getText().toString().trim());
            editor.putString("MatKhau", editTextMatKhau.getText().toString().trim());
            editor.putBoolean("Luu", checkBoxLuu.isChecked());
        } else {
            editor.clear();
        }
        editor.commit();
    }
    //Buoc71 6 Gọi hàm onPause
    @Override
    protected void onPause() {
        super.onPause();
        //Gọi hàm onPause
        SavePreferences();
    }
    //Bước 8 Gọi hàm chứa các sự kiện
    private void AddEvents(){
        //Bước 7.1 Gán sự kiện lên nút đăng nhập
        buttonDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taiKhoan = editTextTaiKhoan.getText().toString().trim();
                String matKhau = editTextMatKhau.getText().toString().trim();
                if(taiKhoan.equals("admin") && matKhau.equals("123")){
                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                        finish();

                    Intent intent = new Intent(MainActivity.this, HeThongActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void RestorePreferences() {
        SharedPreferences preferences = getSharedPreferences(perferName, MODE_PRIVATE);

        boolean luuThongTin = preferences.getBoolean("Luu", false);
        if (luuThongTin) {
            String taiKhoan = preferences.getString("TaiKhoan", "");
            String matKhau = preferences.getString("MatKhau", "");
            editTextTaiKhoan.setText(taiKhoan);
            editTextMatKhau.setText(matKhau);
        }
        checkBoxLuu.setChecked(luuThongTin);
    }
    @Override
    protected void onResume() {
        super.onResume();
        RestorePreferences();
    }
}