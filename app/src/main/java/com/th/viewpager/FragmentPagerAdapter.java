package com.th.viewpager;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class FragmentPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private final FragmentManager mFragmentManager;
    private final List<Fragment> mFragmentList;
    private int[] mIdArray;

    FragmentPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull List<Fragment> fragmentList) {
        mFragmentManager = fragmentManager;
        mFragmentList = fragmentList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FrameLayout frameLayout = new FrameLayout(parent.getContext());
        frameLayout.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new RecyclerView.ViewHolder(frameLayout) {
        };
    }

    @Override
    public long getItemId(int position) {
        int id = mFragmentList.get(position).getId();
        if (id == 0) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                while (true) {
                    final int result = sNextGeneratedId.get();
                    // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                    int newValue = result + 1;
                    if (newValue > 0x00FFFFFF)
                        newValue = 1; // Roll over to 1, not 0.
                    if (sNextGeneratedId.compareAndSet(result, newValue)) {
                        id = result;
                        break;
                    }
                }
            } else {
                id = View.generateViewId();
            }
        }
        mIdArray[position] = id;
        return id;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Fragment oldFragment = mFragmentManager.findFragmentById(holder.itemView.getId());
        if (oldFragment != null) {
            mFragmentManager.beginTransaction().remove(oldFragment).commitNowAllowingStateLoss();
        }
        holder.itemView.setId((int) getItemId(position));
        holder.itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                holder.itemView.removeOnAttachStateChangeListener(this);
                Fragment fragment = mFragmentList.get(holder.getAdapterPosition());
                if (mFragmentManager.findFragmentById(fragment.getId()) != null) {
                    mFragmentManager.beginTransaction().remove(fragment).add(holder.itemView.getId(), fragment).commitNowAllowingStateLoss();
                } else {
                    mFragmentManager.beginTransaction().add(holder.itemView.getId(), fragment).commitNowAllowingStateLoss();
                }
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
            }
        });
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
        final int size = mFragmentList.size();
        if (mIdArray == null || size != mIdArray.length) mIdArray = new int[size];
        return size;
    }
}
