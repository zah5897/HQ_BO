package com.zhan.bottle.ui.activitys;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhan.bottle.R;
import com.zhan.bottle.model.MainTab;
import com.zhan.bottle.ui.adapters.main.MainTabAdapter;
import com.zhan.bottle.ui.fragments.main.TabFoundFragment;
import com.zhan.bottle.ui.fragments.main.TabMeFragment;
import com.zhan.bottle.ui.fragments.main.TabSeaFragment;
import com.zhan.bottle.ui.fragments.main.TabWishFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    //Tab 数目
    private MainTabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        List<MainTab> tabs = initTab();
        initTab(tabs);
        adapter = new MainTabAdapter(getSupportFragmentManager(), tabs);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewpager));
    }


    private List<MainTab> initTab() {
        List<MainTab> tabs = new ArrayList<>();
        tabs.add(new MainTab(R.string.tab_sea, R.drawable.tab_sea_selector, TabSeaFragment.class));
        tabs.add(new MainTab(R.string.tab_find, R.drawable.tab_found_selector, TabFoundFragment.class));
        tabs.add(new MainTab(R.string.tab_wish, R.drawable.tab_wish_selector, TabWishFragment.class));
        tabs.add(new MainTab(R.string.tab_me, R.drawable.tab_me_selector, TabMeFragment.class));
        return tabs;
    }

    private void initTab(List<MainTab> tabs) {
        LayoutInflater inflater = getLayoutInflater();
        for (MainTab tab : tabs) {
            TabLayout.Tab tabView = tablayout.newTab();
            View view = inflater.inflate(R.layout.main_tab_custom, null);
            tabView.setCustomView(view);

            TextView tvTitle = (TextView) view.findViewById(R.id.tv_tab);
            tvTitle.setText(getString(tab.txt));
            ImageView imgTab = (ImageView) view.findViewById(R.id.img_tab);
            imgTab.setImageResource(tab.icon);
            tablayout.addTab(tabView);
        }

    }
}
