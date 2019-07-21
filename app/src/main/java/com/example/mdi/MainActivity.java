package com.example.mdi;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tl_main_category);
        viewPager = (ViewPager) findViewById(R.id.vp_main);

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), 4);
        viewPager.setAdapter(mainPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

        View navCategoryMainLayout = getLayoutInflater().inflate(R.layout.navigation_category_main, null);
        tabLayout.getTabAt(0).setCustomView(navCategoryMainLayout.findViewById(R.id.rl_nav_category_main_check));
        tabLayout.getTabAt(1).setCustomView(navCategoryMainLayout.findViewById(R.id.rl_nav_category_main_select));
        tabLayout.getTabAt(2).setCustomView(navCategoryMainLayout.findViewById(R.id.rl_nav_category_main_setting));
        tabLayout.getTabAt(3).setCustomView(navCategoryMainLayout.findViewById(R.id.rl_nav_category_main_calendar));

        //tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}

