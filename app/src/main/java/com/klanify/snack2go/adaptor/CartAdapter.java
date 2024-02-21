package com.klanify.snack2go.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.klanify.snack2go.helper.ManagementCart;
import com.klanify.snack2go.logic.Producto;
import com.klanify.snack2go.interfaces.ChangeNumberItemsListener;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    /**
     *
     */
    private ArrayList<Producto> productos;
    private ManagementCart managementCart;
    private ChangeNumberItemsListener changeNumberItemsListener;

    public CartAdapter(ArrayList<Producto> productos, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.productos = productos;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.itemProductName.setText(productos.get(position).getNombre());
        String price = String.valueOf(productos.get(position).getPrecio());
        holder.itemPriceText.setText(price);
        holder.number.setText(String.valueOf(productos.get(position).getNumberInCart()));
        holder.itemTotalPriceText.setText(String.valueOf(productos.get(position).getPrecio() * productos.get(position).getNumberInCart()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(productos.get(position).getImagen(),"mipmap",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.itemProductImage);

        holder.minusitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.minusNumberProduct(productos, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });

        holder.plusitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.plusNumberProduct(productos, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemProductName,itemPriceText;
        ImageView itemProductImage,plusitem, minusitem;
        TextView itemTotalPriceText, number;
        ConstraintLayout mainLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemProductName = itemView.findViewById(R.id.itemProductName);
            itemPriceText = itemView.findViewById(R.id.itemPriceText);
            itemProductImage = itemView.findViewById(R.id.itemProductImage);
            itemTotalPriceText = itemView.findViewById(R.id.itemTotalPriceText);
            plusitem = itemView.findViewById(R.id.plusBtnCart);
            minusitem = itemView.findViewById(R.id.minusBtnCart);
            number = itemView.findViewById(R.id.numberItemText);

            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
