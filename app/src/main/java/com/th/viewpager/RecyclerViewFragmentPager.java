package com.th.viewpager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.NestedScrollingParent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.List;

public class RecyclerViewFragmentPager extends RecyclerView implements NestedScrollingParent {

    public RecyclerViewFragmentPager(Context context) {
        super(context);
        init(context);
    }

    public RecyclerViewFragmentPager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        new PagerSnapHelper().attachToRecyclerView(this);
    }


    public void addFragments(FragmentManager fragmentManager, List<Fragment> fragmentList) {
        RecyclerViewFragmentPagerAdapter adapter = new RecyclerViewFragmentPagerAdapter(fragmentManager, fragmentList);
        setAdapter(adapter);
    }
}
