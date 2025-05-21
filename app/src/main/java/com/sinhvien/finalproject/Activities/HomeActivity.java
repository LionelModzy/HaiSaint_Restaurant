package com.sinhvien.finalproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.sinhvien.finalproject.DAO.NhanVienDAO;
import com.sinhvien.finalproject.DTO.ThanhToanDTO;
import com.sinhvien.finalproject.Fragments.DisplayHomeFragment;
import com.sinhvien.finalproject.Fragments.DisplayStaffFragment;
import com.sinhvien.finalproject.Fragments.DisplayInformationFragment;
import com.sinhvien.finalproject.Fragments.DisplayStatisticFragment;
import com.sinhvien.finalproject.Fragments.DisplayTableFragment;
import com.sinhvien.finalproject.R;

import java.util.List;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    MenuItem selectedFeature, selectedManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    TextView TXT_menu_tennv, TXT_menu_hotennv;
    int maquyen = 0, manv;
    SharedPreferences sharedPreferences;
    BottomNavigationView bot_nav;
    List<ThanhToanDTO> thanhToanDTOList;
    NhanVienDAO nhanVienDAO;

    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    //hiển thị tương ứng trên navigation
                    FragmentTransaction tranDisplayHome = fragmentManager.beginTransaction();
                    DisplayHomeFragment displayHomeFragment = new DisplayHomeFragment();
                    tranDisplayHome.replace(R.id.contentView,displayHomeFragment);
                    tranDisplayHome.commit();
                    navigationView.setCheckedItem(item.getItemId());
                    return true;

                case R.id.nav_staff:
                    if(maquyen == 1){
                        //hiển thị tương ứng trên navigation
                        FragmentTransaction tranDisplayStaff = fragmentManager.beginTransaction();
                        DisplayStaffFragment displayStaffFragment = new DisplayStaffFragment();
                        tranDisplayStaff.replace(R.id.contentView,displayStaffFragment);
                        tranDisplayStaff.commit();
                        navigationView.setCheckedItem(item.getItemId());
                    }else {
                        Toast.makeText(getApplicationContext(),"Bạn không có quyền truy cập",Toast.LENGTH_SHORT).show();
                    }

                    return true;

                case R.id.nav_information:
                    //hiển thị tương ứng trên navigation
                    FragmentTransaction tranDisplayStatistic = fragmentManager.beginTransaction();
                    DisplayInformationFragment displayInformationFragment = new DisplayInformationFragment();
                    tranDisplayStatistic.replace(R.id.contentView, displayInformationFragment);
                    tranDisplayStatistic.commit();
                    navigationView.setCheckedItem(item.getItemId());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bot_nav = navigation; // Đảm bảo bot_nav đã được ánh xạ
        //region thuộc tính bên view
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view_trangchu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        View view = navigationView.getHeaderView(0);
        TXT_menu_tennv = (TextView) view.findViewById(R.id.txt_menu_tennv);
        //endregion
        //xử lý toolbar và navigation
        setSupportActionBar(toolbar); //tạo toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Trang chủ");
        navigationView.setNavigationItemSelectedListener(this);

        //tạo nút mở navigation
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar
        ,R.string.opentoggle,R.string.closetoggle){
            @Override
            public void onDrawerOpened(View drawerView) {    super.onDrawerOpened(drawerView); }

            @Override
            public void onDrawerClosed(View drawerView) {    super.onDrawerClosed(drawerView); }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //lấy file share prefer
        sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);
        //hiện thị fragment home mặc định
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tranDisplayHome = fragmentManager.beginTransaction();
        DisplayHomeFragment displayHomeFragment = new DisplayHomeFragment();
        tranDisplayHome.replace(R.id.contentView, displayHomeFragment);
        tranDisplayHome.commit();
        navigationView.setCheckedItem(R.id.nav_home);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Kiểm tra nếu bot_nav đã được khởi tạo
        if (bot_nav != null) {
            // Cập nhật Bottom Navigation để chọn item
            bot_nav.setSelectedItemId(item.getItemId());
        } else {
            Log.e("HomeActivity", "BottomNavigationView is null");
        }

        // Xử lý khi chọn item từ Navigation Drawer
        switch (item.getItemId()) {
            case R.id.nav_home:
                // Chuyển đến fragment Trang chủ
                FragmentTransaction tranHome = fragmentManager.beginTransaction();
                tranHome.replace(R.id.contentView, new DisplayHomeFragment());
                tranHome.commit();
                break;

            case R.id.nav_staff:
                if (maquyen == 1) {
                    // Chuyển đến fragment Nhân viên
                    FragmentTransaction tranStaff = fragmentManager.beginTransaction();
                    tranStaff.replace(R.id.contentView, new DisplayStaffFragment());
                    tranStaff.commit();
                } else {
                    Toast.makeText(this, "Bạn không có quyền truy cập", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.nav_information:
                // Chuyển đến fragment Thông tin
                FragmentTransaction tranInfo = fragmentManager.beginTransaction();
                tranInfo.replace(R.id.contentView, new DisplayInformationFragment());
                tranInfo.commit();
                break;
            case R.id.nav_statistics:
                FragmentTransaction tranStatistic = fragmentManager.beginTransaction();
                tranStatistic.replace(R.id.contentView, new DisplayStatisticFragment());
                tranStatistic.commit();
                break;

            case R.id.nav_add_category:
                Intent intent = new Intent(this, AddCategoryActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_tables:
                FragmentTransaction tranTables = fragmentManager.beginTransaction();
                tranTables.replace(R.id.contentView, new DisplayTableFragment());
                tranTables.commit();
                break;

            case R.id.nav_logout:
                // Xử lý đăng xuất, có thể là xóa shared preferences hoặc điều hướng về màn hình đăng nhập
                Intent logoutIntent = new Intent(this, LoginActivity.class);
                startActivity(logoutIntent);
                finish();
                break;
        }
        // Đóng Drawer sau khi xử lý xong
        drawerLayout.closeDrawer(navigationView);
        return true;
    }
}