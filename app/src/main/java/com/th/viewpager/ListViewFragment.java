package com.th.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class ListViewFragment extends ListFragment {

    public static ListViewFragment newInstance() {
        return new ListViewFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(ArrayAdapter.createFromResource(getContext(), R.array.list_items, android.R.layout.simple_list_item_1));
    }
}
