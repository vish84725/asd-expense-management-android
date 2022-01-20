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

public class RecurringSpinnerAdaptor  extends BaseAdapter {
    Context context;
    List<String> recurringTypes;
    LayoutInflater inflter;

    public RecurringSpinnerAdaptor(Context applicationContext, List<String> recurringTypes) {
        this.context = applicationContext;
        this.recurringTypes = recurringTypes;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return recurringTypes.size();
    }

    @Override
    public Object getItem(int i) {
        return recurringTypes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        TextView names = (TextView) view.findViewById(R.id.textView_spinner_value);
        names.setText(recurringTypes.get(i));
        return view;
    }
}
