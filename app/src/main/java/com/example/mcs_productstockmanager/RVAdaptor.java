package com.example.mcs_productstockmanager;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class RVAdaptor extends RecyclerView.Adapter<RVAdaptor.ViewHolder> {
    private OnRecycleClickListener mListener;

    public interface OnRecycleClickListener{
        void onItemClick(int position);
    }

    public void setItemClickListener(OnRecycleClickListener listener){
        mListener = listener;
    }
    Context contexts;
    Cursor res;
    public RVAdaptor(Context context, Cursor cursor) {
        contexts = context;
        res = cursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView descText;
        public TextView qtyText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.tvproductname);
            descText = itemView.findViewById(R.id.tvdesc);
            qtyText = itemView.findViewById(R.id.tvquantity);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contexts);
        View view = inflater.inflate(R.layout.product_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(!res.moveToPosition(position)){
            return;
        }
        String name = res.getString(res.getColumnIndex(DBHelper.COL_2));
        String desc = res.getString(res.getColumnIndex(DBHelper.COL_3));
        String qty = res.getString(res.getColumnIndex(DBHelper.COL_4));
        holder.nameText.setText(name);
        holder.descText.setText(desc);
        holder.qtyText.setText("Quantity: " + qty);
    }

    @Override
    public int getItemCount() {
        return res.getCount();
    }

}
