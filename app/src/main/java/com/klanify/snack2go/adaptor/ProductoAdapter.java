package com.klanify.snack2go.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.klanify.snack2go.R;
import com.klanify.snack2go.logic.Producto;

import java.util.ArrayList;
import java.util.Locale;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder>{
    ArrayList<Producto> productos;

    public ProductoAdapter(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    @NonNull
    @Override
    public ProductoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdapter.ViewHolder holder, int position) {
        holder.productName.setText(productos.get(position).getNombre());
        String price = String.valueOf(productos.get(position).getPrecio());
        String formattedPrice = String.format(Locale.getDefault(), "%.2f", price);
        holder.precioText.setText(formattedPrice);

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(productos.get(position).getImagen(),"mipmap",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView productName,precioText;
        ImageView productImage;
        ConstraintLayout mainLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.titleText);
            precioText = itemView.findViewById(R.id.precioText);
            productImage = itemView.findViewById(R.id.productImage);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
