package com.klanify.snack2go.adaptor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.klanify.snack2go.R;
import com.klanify.snack2go.activity.ProductDetailActivity;
import com.klanify.snack2go.logic.Plato;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{
    ArrayList<Plato> platos;

    public MenuAdapter(ArrayList<Plato> platos) {
        this.platos = platos;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_product,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.titleText.setText(platos.get(position).getNombre());
        holder.priceText.setText(String.valueOf(platos.get(position).getPrecio()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(platos.get(position).getImagen(),"mipmap",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.menuImage);

        holder.add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ProductDetailActivity.class);
                intent.putExtra("object",platos.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return platos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleText, priceText;
        ImageView menuImage;
        Button add_button;
        ConstraintLayout mainLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            priceText = itemView.findViewById(R.id.priceText);
            menuImage = itemView.findViewById(R.id.productImage);
            add_button = itemView.findViewById(R.id.add_button);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
