package com.th.viewpager;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class RecyclerViewFragmentPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private final FragmentManager mFragmentManager;
    private final List<Fragment> mFragmentList;

    RecyclerViewFragmentPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull List<Fragment> fragmentList) {
        mFragmentManager = fragmentManager;
        mFragmentList = fragmentList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_layout, parent, false)) {};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            while (true) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    holder.itemView.setId(result);
                    break;
                }
            }
        } else {
            holder.itemView.setId(View.generateViewId());
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        mFragmentManager.beginTransaction().replace(holder.itemView.getId(), mFragmentList.get(holder.getAdapterPosition())).commit();
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        mFragmentManager.beginTransaction().remove(mFragmentManager.findFragmentById(holder.itemView.getId())).commit();
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }
}
