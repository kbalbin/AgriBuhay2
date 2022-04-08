package com.AgriBuhayProj.app.ProducerProductPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AgriBuhayProj.app.R;
import java.util.List;

public class ProducerPreparedOrderViewAdapter extends RecyclerView.Adapter<ProducerPreparedOrderViewAdapter.ViewHolder> {

    private Context mcontext;
    private List<ProducerFinalOrders> producerFinalOrderslist;

    public ProducerPreparedOrderViewAdapter(Context context, List<ProducerFinalOrders> producerFinalOrderslist) {
        this.producerFinalOrderslist = producerFinalOrderslist;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.producer_preparedorderview, parent, false);
        return new ProducerPreparedOrderViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ProducerFinalOrders producerFinalOrders = producerFinalOrderslist.get(position);
        holder.dishname.setText(producerFinalOrders.getProductName());
        holder.price.setText("Price: ₱ " + producerFinalOrders.getProductPrice());
        holder.quantity.setText("× " + producerFinalOrders.getProductQuantity());
        holder.totalprice.setText("Total: ₱ " + producerFinalOrders.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return producerFinalOrderslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dishname, price, totalprice, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dishname = itemView.findViewById(R.id.Cdishname);
            price = itemView.findViewById(R.id.Cdishprice);
            totalprice = itemView.findViewById(R.id.Ctotalprice);
            quantity = itemView.findViewById(R.id.Cdishqty);
        }
    }
}
