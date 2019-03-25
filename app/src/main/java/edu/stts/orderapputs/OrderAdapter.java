package edu.stts.orderapputs;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends Adapter<OrderAdapter.ViewHolder> {

    private ArrayList<Order> orderList;
    private static RVClickListener myListener;

    public OrderAdapter(ArrayList<Order> orderList, RVClickListener rvcl) {
        this.orderList = orderList;
        myListener = rvcl;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.rowitem, viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tvQtyType.setText(orderList.get(i).getQty()+" "+orderList.get(i).getType());
        viewHolder.tvSubtotal.setText(orderList.get(i).getSubtotal()+"");

    }

    @Override
    public int getItemCount() {
        return (orderList!=null)?orderList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvQtyType, tvSubtotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQtyType = itemView.findViewById(R.id.tvQtyType);
            tvSubtotal = itemView.findViewById(R.id.tvSubtotal);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    myListener.recyclerViewClick(v, ViewHolder.this.getLayoutPosition());
                }
            });
        }
    }
}
