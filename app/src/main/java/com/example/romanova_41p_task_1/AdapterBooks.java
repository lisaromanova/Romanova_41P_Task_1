package com.example.romanova_41p_task_1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

public class AdapterBooks extends BaseAdapter{
    Context mContext;
    List<Books> booksList;

    public AdapterBooks(Context mContext, List<Books> booksList) {
        this.mContext = mContext;
        this.booksList = booksList;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
