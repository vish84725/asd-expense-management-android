package com.cube365.asdexpensemanagement.adaptors;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cube365.asdexpensemanagement.R;
import com.cube365.asdexpensemanagement.models.categories.GetCategoryResponse;
import com.cube365.asdexpensemanagement.utils.Constants;
import com.google.android.material.slider.Slider;

import java.util.List;

public class CategoriesAdaptor extends RecyclerView.Adapter<CategoriesAdaptor.ViewHolder>{
    private List<GetCategoryResponse> mCategories;
    private static ItemClickListener mClickListener;

    public CategoriesAdaptor(List<GetCategoryResponse> categories) {
        mCategories = categories;
    }

    @NonNull
    @Override
    public CategoriesAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_row_category, parent, false);

        return new CategoriesAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdaptor.ViewHolder holder, int position) {
        try{

            holder.getTextViewName().setText(mCategories.get(position).getName());
//            holder.getSliderBudget().setText(mCategories.get(position).getDescription());

        }catch (Exception ex){
            Log.d(Constants.ErrorMessage.ERROR_TAG,ex.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public GetCategoryResponse getTransaction(int id) {
        return mCategories.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textViewName;
        private final Slider sliderBudget;
        private final CardView containerView;
        public ViewHolder(View view) {
            super(view);

            textViewName = (TextView) view.findViewById(R.id.textView_categoryTitle);
            sliderBudget = (Slider) view.findViewById(R.id.slider);
            containerView = (CardView)view.findViewById(R.id.cardView_container);


        }

        public TextView getTextViewName() { return textViewName; }
        public Slider getSliderBudget() { return sliderBudget; }

        public CardView getCardViewContainer() {
            return containerView;
        }



        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
