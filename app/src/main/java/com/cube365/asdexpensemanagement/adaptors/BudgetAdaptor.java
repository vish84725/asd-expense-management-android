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
import com.cube365.asdexpensemanagement.models.transactions.GetBudgetResponse;
import com.cube365.asdexpensemanagement.utils.Constants;
import com.google.android.material.slider.Slider;

import java.util.List;

public class BudgetAdaptor extends RecyclerView.Adapter<BudgetAdaptor.ViewHolder>{
    private List<GetBudgetResponse> mBudgets;
    private static ItemClickListener mClickListener;

    public BudgetAdaptor(List<GetBudgetResponse> budgets) {
        mBudgets = budgets;
    }

    @NonNull
    @Override
    public BudgetAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_row_budget, parent, false);

        return new BudgetAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetAdaptor.ViewHolder holder, int position) {
        try{

            holder.getTextViewName().setText(mBudgets.get(position).getCategoryName());
            holder.getTextViewTotalBudget().setText(mBudgets.get(position).getBudgetAmount().toString());
            holder.getTextViewTotalTransactions().setText(mBudgets.get(position).getTransactionAmount().toString());

            holder.getSliderBudget().setValue(Float.parseFloat(mBudgets.get(position).getTransactionAmount().toString()));
            holder.getSliderBudget().setValueFrom(0);
            holder.getSliderBudget().setValueTo(Float.parseFloat(mBudgets.get(position).getBudgetAmount().toString()));

            final int red = holder.getCardViewContainer().getContext().getResources().getColor(R.color.wms_red);
            final int green = holder.getCardViewContainer().getContext().getResources().getColor(R.color.wms_green);

            if(mBudgets.get(position).getTransactionAmount().equals(mBudgets.get(position).getBudgetAmount())){
                holder.getCardViewContainer().setCardBackgroundColor(red);
            }else if(mBudgets.get(position).getTransactionAmount()  < mBudgets.get(position).getBudgetAmount()/2){
                holder.getCardViewContainer().setCardBackgroundColor(green);
            }

        }catch (Exception ex){
            Log.d(Constants.ErrorMessage.ERROR_TAG,ex.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mBudgets.size();
    }

    public GetBudgetResponse getBudget(int id) {
        return mBudgets.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textViewName;
        private final TextView textViewTotalTransactions;
        private final TextView textViewTotalBudget;
        private final Slider sliderBudget;
        private final CardView containerView;
        public ViewHolder(View view) {
            super(view);

            textViewName = (TextView) view.findViewById(R.id.textView_categoryTitle);
            textViewTotalTransactions = (TextView) view.findViewById(R.id.textView_totalTransactions);
            textViewTotalBudget = (TextView) view.findViewById(R.id.textView_totalBudget);
            sliderBudget = (Slider) view.findViewById(R.id.slider);
            containerView = (CardView)view.findViewById(R.id.cardView_container);


        }

        public TextView getTextViewName() { return textViewName; }
        public TextView getTextViewTotalTransactions() { return textViewTotalTransactions; }
        public TextView getTextViewTotalBudget() { return textViewTotalBudget; }

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
