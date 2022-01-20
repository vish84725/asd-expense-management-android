package com.cube365.asdexpensemanagement.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cube365.asdexpensemanagement.R;

import com.cube365.asdexpensemanagement.models.categories.GetCategoryResponse;

import java.util.List;

public class SpinnerAdaptor  extends BaseAdapter {
    Context context;
    List<GetCategoryResponse> categories;
    LayoutInflater inflter;

    public SpinnerAdaptor(Context applicationContext, List<GetCategoryResponse> categories) {
        this.context = applicationContext;
        this.categories = categories;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        TextView names = (TextView) view.findViewById(R.id.textView_spinner_value);
        names.setText(categories.get(i).getName());
        return view;
    }
}
