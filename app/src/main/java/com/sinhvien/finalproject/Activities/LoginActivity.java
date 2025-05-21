package com.sinhvien.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.finalproject.DAO.NhanVienDAO;
import com.sinhvien.finalproject.R;

public class LoginActivity extends AppCompatActivity {
    Button BTN_login_DangNhap, BTN_login_DangKy, BTN_login_QuenMatKhau;
    TextInputLayout TXTL_login_TenDN, TXTL_login_MatKhau;
    NhanVienDAO nhanVienDAO;
    private View view;
    public static final String BUNDLE = "BUNDLE";
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    CheckBox ckbox;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        //thuộc tính view
        TXTL_login_TenDN = (TextInputLayout)findViewById(R.id.txtl_login_TenDN);
        TXTL_login_MatKhau = (TextInputLayout)findViewById(R.id.txtl_login_MatKhau);
        BTN_login_DangNhap = (Button)findViewById(R.id.btn_login_DangNhap);
        BTN_login_QuenMatKhau = (Button)findViewById(R.id.btn_login_quenmatkhau);


        nhanVienDAO = new NhanVienDAO(this);    //khởi tạo kết nối csdl
        ckbox = findViewById(R.id.ckbox); // Checkbox để lưu tài khoản
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        loadUserInfo(); // Gọi phương thức để tải dữ liệu đã lưu

        BTN_login_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUserName() | !validatePassWord()) {
                    return;
                }

                String tenDN = TXTL_login_TenDN.getEditText().getText().toString();
                String matKhau = TXTL_login_MatKhau.getEditText().getText().toString();
                int ktra = nhanVienDAO.KiemTraDN(tenDN, matKhau);
                int maquyen = nhanVienDAO.LayQuyenNV(ktra);

                if (ktra != 0) {
                    if (ckbox.isChecked()) { // Lưu thông tin nếu checkbox được chọn
                        saveUserInfo(tenDN, matKhau);
                    }

                    // Lưu mã quyền vào SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("maquyen", maquyen);
                    editor.commit();

                    // Gửi dữ liệu user qua trang chủ
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("tendn", tenDN);
                    intent.putExtra("matkhau", matKhau);
                    intent.putExtra("manv", ktra);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        BTN_login_QuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PasswordActivity.class);
                startActivity(intent);
            }

        });
    }

    private void saveUserInfo(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    private void loadUserInfo() {
        String savedUsername = sharedPreferences.getString(KEY_USERNAME, "");
        String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");
        TXTL_login_TenDN.getEditText().setText(savedUsername);
        TXTL_login_MatKhau.getEditText().setText(savedPassword);
        ckbox.setChecked(!savedUsername.isEmpty() && !savedPassword.isEmpty());
    }

    //Hàm quay lại màn hình chính
    public void backFromLogin(View view)
    {
        Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
        //tạo animation cho thành phần
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.layoutLogin),"transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }

    //Hàm chuyển qua trang đăng ký
    public void callRegisterFromLogin(View view)
    {
        this.view = view;
        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //region Validate field
    private boolean validateUserName(){
        String val = TXTL_login_TenDN.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_login_TenDN.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_login_TenDN.setError(null);
            TXTL_login_TenDN.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = TXTL_login_MatKhau.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_login_MatKhau.setError(getResources().getString(R.string.not_empty));
            return false;
        }else{
            TXTL_login_MatKhau.setError(null);
            TXTL_login_MatKhau.setErrorEnabled(false);
            return true;
        }
    }
    //endregion
}