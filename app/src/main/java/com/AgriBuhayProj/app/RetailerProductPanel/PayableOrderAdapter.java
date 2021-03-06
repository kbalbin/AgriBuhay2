package com.AgriBuhayProj.app.RetailerProductPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AgriBuhayProj.app.R;
import java.util.List;

public class PayableOrderAdapter extends RecyclerView.Adapter<PayableOrderAdapter.ViewHolder> {

    private Context context;
    private List<RetailerPaymentOrders> retailerPaymentOrderslist;

    public PayableOrderAdapter(Context context, List<RetailerPaymentOrders> customerPendingOrderslist) {
        this.retailerPaymentOrderslist = customerPendingOrderslist;
        this.context = context;
    }

    @NonNull
    @Override
    public PayableOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.retailer_payableorder, parent, false);
        return new PayableOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PayableOrderAdapter.ViewHolder holder, int position) {

        final RetailerPaymentOrders retailerPaymentOrders = retailerPaymentOrderslist.get(position);
        holder.Productname.setText(retailerPaymentOrders.getProductName());
        holder.Price.setText("Price: ₱ " + retailerPaymentOrders.getProductPrice());
        holder.Quantity.setText("× " + retailerPaymentOrders.getProductQuantity());
        holder.Totalprice.setText("Total: ₱ " + retailerPaymentOrders.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return retailerPaymentOrderslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Productname, Price, Quantity, Totalprice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Productname = itemView.findViewById(R.id.dish);
            Price = itemView.findViewById(R.id.pri);
            Quantity = itemView.findViewById(R.id.qt);
            Totalprice = itemView.findViewById(R.id.Tot);
        }
    }
}
