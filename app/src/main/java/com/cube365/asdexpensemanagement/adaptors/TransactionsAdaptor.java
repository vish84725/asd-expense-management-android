package com.cube365.asdexpensemanagement.adaptors;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cube365.asdexpensemanagement.R;
import com.cube365.asdexpensemanagement.models.transactions.TransactionResponse;
import com.cube365.asdexpensemanagement.utils.Constants;

import java.util.List;

public class TransactionsAdaptor extends RecyclerView.Adapter<TransactionsAdaptor.ViewHolder>{
    private List<TransactionResponse> mTransactions;
    private static ItemClickListener mClickListener;

    public TransactionsAdaptor(List<TransactionResponse> transactions) {
        mTransactions = transactions;
    }

    @NonNull
    @Override
    public TransactionsAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_row_transactions, parent, false);

        return new TransactionsAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsAdaptor.ViewHolder holder, int position) {
        try{

            holder.getTextViewAmount().setText(mTransactions.get(position).getAmountString());
            holder.getTextViewTitle().setText(mTransactions.get(position).getDescription());
            holder.getTextViewNotes().setText(mTransactions.get(position).getNote());
            holder.getTextViewCategory().setText(mTransactions.get(position).getCategory().getName());
            holder.getTextViewDate().setText(mTransactions.get(position).getCreateDate().toString());
            holder.getTextViewRecurring().setText(mTransactions.get(position).getRecurringType());

            final int blue = holder.getCardViewContainer().getContext().getResources().getColor(R.color.wms_blue);
            final int green = holder.getCardViewContainer().getContext().getResources().getColor(R.color.wms_green);
            final int yelllow = holder.getCardViewContainer().getContext().getResources().getColor(R.color.wms_yellow);
            final int red = holder.getCardViewContainer().getContext().getResources().getColor(R.color.wms_red);

            switch (mTransactions.get(position).getTransactionType()){
                case "INCOME":
                    holder.getCardViewContainer().setCardBackgroundColor(green);
                    break;
                case "EXPENSE":
                    holder.getCardViewContainer().setCardBackgroundColor(blue);
                    break;
            }

        }catch (Exception ex){
            Log.d(Constants.ErrorMessage.ERROR_TAG,ex.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    public TransactionResponse getTransaction(int id) {
        return mTransactions.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textViewAmount,textViewTitle,textViewNotes,textViewRecurring,
                textViewDate,textViewCategory;
        private final CardView containerView;
        public ViewHolder(View view) {
            super(view);

            textViewAmount = (TextView) view.findViewById(R.id.textView_transactionAmount);
            textViewTitle = (TextView) view.findViewById(R.id.textView_transactionTitle);
            textViewNotes = (TextView) view.findViewById(R.id.textView_transactionNote);
            textViewDate = (TextView) view.findViewById(R.id.textView_transationb_date);
            textViewRecurring = (TextView) view.findViewById(R.id.textView_transactionRecurring);
            textViewCategory = (TextView) view.findViewById(R.id.textView_transaction_category);

            containerView = (CardView)view.findViewById(R.id.cardView_container);


        }

        public TextView getTextViewAmount() { return textViewAmount; }
        public TextView getTextViewTitle() { return textViewTitle; }
        public TextView getTextViewNotes() { return textViewNotes; }
        public TextView getTextViewDate() { return textViewDate; }
        public TextView getTextViewCategory() { return textViewCategory; }
        public TextView getTextViewRecurring() { return textViewRecurring; }

        public CardView getCardViewContainer() {
            return containerView;
        }



        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
