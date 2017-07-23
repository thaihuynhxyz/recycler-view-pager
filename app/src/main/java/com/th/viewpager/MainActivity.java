package com.th.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerViewPager pager = (RecyclerViewPager) findViewById(R.id.pager);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(CoordinatorFragment.newInstance());
        fragmentList.add(ScrollViewFragment.newInstance());
        fragmentList.add(ListViewFragment.newInstance());
        fragmentList.add(RecyclerViewFragment.newInstance());
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        pager.setAdapter(adapter);
        pager.setItemViewCacheSize(4);
    }
}
