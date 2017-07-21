package com.th.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerViewFragmentPager pager = (RecyclerViewFragmentPager) findViewById(R.id.pager);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(CoordinatorFragment.newInstance());
        fragmentList.add(ScrollViewFragment.newInstance());
        fragmentList.add(ListViewFragment.newInstance());
        fragmentList.add(RecyclerViewFragment.newInstance());
        pager.addFragments(getSupportFragmentManager(), fragmentList);

//        ViewPager pager = (ViewPager) findViewById(R.id.pager);
//        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                switch (position) {
//                    case 0:
//                        return CoordinatorFragment.newInstance();
//                    case 1:
//                        return ScrollViewFragment.newInstance();
//                    case 2:
//                        return ListViewFragment.newInstance();
//                    default:
//                        return RecyclerViewFragment.newInstance();
//                }
//            }
//
//            @Override
//            public int getCount() {
//                return 4;
//            }
//        });
    }
}
