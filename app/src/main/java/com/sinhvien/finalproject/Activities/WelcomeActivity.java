package com.sinhvien.finalproject.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.sinhvien.finalproject.R;

import java.util.ArrayList;
import java.util.Locale;


public class WelcomeActivity extends AppCompatActivity{

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

        fragmentManager = getSupportFragmentManager();

        final PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataForOnBoarding());

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_ob, paperOnboardingFragment);
        fragmentTransaction.commit();
    }



    private ArrayList<PaperOnboardingPage> getDataForOnBoarding() {

        PaperOnboardingPage src1 = new PaperOnboardingPage(
                "Chào mừng đến với Hai Saint Restaurant",
                "Khám phá cách sử dụng ứng dụng để quản lý đơn hàng và phục vụ khách hàng một cách nhanh chóng và hiệu quả.",
                Color.parseColor("#50AEFBFF"),
                R.drawable.logo_icon_haisaint,
                R.drawable.circle_drawable
        );

        PaperOnboardingPage src2 = new PaperOnboardingPage(
                "Quản lý đơn hàng hiệu quả",
                "Quản lý đơn hàng từ khi tiếp nhận cho đến khi giao cho khách hàng, đảm bảo sự nhanh chóng và chính xác.",
                Color.parseColor("#50AEFBFF"),
                R.drawable.slide_restaurant1,
                R.drawable.circle_drawable
        );

        PaperOnboardingPage src3 = new PaperOnboardingPage(
                "Dễ dàng sử dụng",
                "Ứng dụng có giao diện thân thiện và dễ sử dụng, giúp nhân viên làm quen chỉ trong vài phút để phục vụ khách hàng tốt hơn.",
                Color.parseColor("#50AEFBFF"),
                R.drawable.slide_restaurant3,
                R.drawable.circle_drawable
        );

        PaperOnboardingPage src4 = new PaperOnboardingPage(
                "Tiết kiệm chi phí",
                "Ứng dụng giúp bạn tiết kiệm chi phí quản lý và hỗ trợ miễn phí trong suốt quá trình sử dụng.",
                Color.parseColor("#50AEFBFF"),
                R.drawable.slide_restaurant2,
                R.drawable.circle_drawable
        );

        ArrayList<PaperOnboardingPage> element = new ArrayList<>();
        element.add(src1);
        element.add(src2);
        element.add(src3);
        element.add(src4);
        return element;



    }

    ;



    //chuyển sang trang đăng nhập
    public void callLoginFromWel(View view) {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE); // Hiển thị ProgressBar

        new android.os.Handler().postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<>(findViewById(R.id.btn_login), "transition_login");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this, pairs);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
            progressBar.setVisibility(View.GONE); // Ẩn ProgressBar sau khi chuyển
        }, 400); // Delay 500ms để hiển thị hiệu ứng rõ ràng
    }

    public void callSignUpFromWel(View view) {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new android.os.Handler().postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<>(findViewById(R.id.btn_signup), "transition_signup");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this, pairs);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
            progressBar.setVisibility(View.GONE);
        }, 400);
    }


}