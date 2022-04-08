package com.AgriBuhayProj.app.ProducerProductPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.AgriBuhayProj.app.R;

import java.util.List;

public class ProducerhomeAdapter extends RecyclerView.Adapter<ProducerhomeAdapter.ViewHolder> {

   private Context mcont;
   private List<UpdateProductModel> updateProductModellist;

   public ProducerhomeAdapter(Context context, List<UpdateProductModel> updateProductModellist)
   {
       this.updateProductModellist = updateProductModellist;
       this.mcont=context;
   }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(mcont).inflate(R.layout.producer_menu_update_delete,parent,false);
       return new ProducerhomeAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       final UpdateProductModel updateProductModel = updateProductModellist.get(position);
       holder.products.setText(updateProductModel.getProducts());
       updateProductModel.getRandomUID();
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(mcont, Update_Delete_Product.class);
               intent.putExtra("updatedeleteproduct", updateProductModel.getRandomUID());
               mcont.startActivity(intent);

           }
       });
    }


    @Override
    public int getItemCount() {
        return updateProductModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       TextView products;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //TODO changed R.id.dish_name to productt_name since product_Name is already taken
            products =itemView.findViewById(R.id.productt_name);

        }
    }
}
