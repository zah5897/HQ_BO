package com.zhan.bottle.ui.adapters.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhan.bottle.model.MainTab;

import java.util.List;

/**
 * Created by zah on 2017/6/27.
 */

public class MainTabAdapter extends FragmentPagerAdapter {
    private List<MainTab> tabs;

    public MainTabAdapter(FragmentManager fm, List<MainTab> tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        MainTab tab = tabs.get(position);
        try {
            return (Fragment) Class.forName(tab.clazz.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
