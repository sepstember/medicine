package com.example.mdi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;

    public MainPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CheckingFragment checkingFragment = new CheckingFragment();
                return checkingFragment;

            case 1:
                SelectingFragment selectingFragment = new SelectingFragment();
                return selectingFragment;

            case 2:
                SettingFragment settingFragment = new SettingFragment();
                return settingFragment;

            case 3:
                CalendarFragment calendarFragment = new CalendarFragment();
                return calendarFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
