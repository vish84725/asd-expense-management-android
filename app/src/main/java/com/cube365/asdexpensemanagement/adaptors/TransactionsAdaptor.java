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

//            holder.getTextViewItemCode().setText(mTransactions.get(position).getItemCodeText());
//            holder.getTextViewItemName().setText(mBinDetails.get(position).getItemDescription());
//            holder.getTextViewInvoiceId().setText(mBinDetails.get(position).getDocNumberText());
//            holder.getTextViewPickedQuantity().setText(mBinDetails.get(position).getLastPickQuantityText());
//            holder.getTextViewQuantity().setText(mBinDetails.get(position).getRecievedQuantityText());

//            if(mBinDetails.get(position).getChecked()){
//                holder.getSwitchIsCompleted().setChecked(true);
//                holder.getSwitchIsCompleted().setEnabled(false);
//            }else{
//                holder.getSwitchIsCompleted().setChecked(false);
//            }
//
//            final int blue = holder.getCardViewContainer().getContext().getResources().getColor(R.color.wms_blue);
//            final int green = holder.getCardViewContainer().getContext().getResources().getColor(R.color.wms_green);
//            final int yelllow = holder.getCardViewContainer().getContext().getResources().getColor(R.color.wms_yellow);
//            final int red = holder.getCardViewContainer().getContext().getResources().getColor(R.color.wms_red);
//            InvoicePickedStatusEnum ss = mBinDetails.get(position).getInvoiceStatus();
//            switch (mBinDetails.get(position).getInvoiceStatus()){
//                case COMPLETED:
//                    holder.getCardViewContainer().setCardBackgroundColor(green);
//                    holder.getCardViewQtyContainer().setCardBackgroundColor(green);
//                    holder.getSwitchIsCompleted().setChecked(true);
//                    holder.getSwitchIsCompleted().setEnabled(false);
//                    break;
//                case FLAGGED:
//                    holder.getCardViewContainer().setCardBackgroundColor(red);
//                    holder.getCardViewQtyContainer().setCardBackgroundColor(red);
//                    holder.getSwitchIsCompleted().setEnabled(false);
//                    holder.getSwitchIsCompleted().setChecked(true);
//                    break;
//                case IN_PROGRESS:
//                    holder.getCardViewContainer().setCardBackgroundColor(yelllow);
//                    holder.getCardViewQtyContainer().setCardBackgroundColor(yelllow);
//                    holder.getSwitchIsCompleted().setEnabled(true);
//                    break;
//                case INITIAL:
//                    holder.getCardViewContainer().setCardBackgroundColor(blue);
//                    holder.getCardViewQtyContainer().setCardBackgroundColor(blue);
//                    holder.getSwitchIsCompleted().setEnabled(true);
//                    break;
//            }

        }catch (Exception ex){
            Log.d(Constants.ErrorMessage.ERROR_TAG,ex.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return 10;
        //return mTransactions.size();
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
        private final TextView textViewAmount,textViewTitle,textViewNotes,
                textViewDate,textViewCategory;
        private final CardView containerView;
        public ViewHolder(View view) {
            super(view);

            textViewAmount = (TextView) view.findViewById(R.id.textView_transactionAmount);
            textViewTitle = (TextView) view.findViewById(R.id.textView_transactionTitle);
            textViewNotes = (TextView) view.findViewById(R.id.textView_transactionNote);
            textViewDate = (TextView) view.findViewById(R.id.textView_transationb_date);
            textViewCategory = (TextView) view.findViewById(R.id.textView_transaction_category);

            containerView = (CardView)view.findViewById(R.id.cardView_container);


        }

        public TextView getTextViewAmount() { return textViewAmount; }
        public TextView getTextViewTitle() { return textViewTitle; }
        public TextView getTextViewNotes() { return textViewNotes; }
        public TextView getTextViewDate() { return textViewDate; }
        public TextView getTextViewCategory() { return textViewCategory; }

        public CardView getCardViewContainer() {
            return containerView;
        }



        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
