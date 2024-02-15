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
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        holder.titleText.setText(platos.get(position).getNombre());
        holder.precioText.setText(String.valueOf(platos.get(position).getPrecio()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(platos.get(position).getImagen(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.menuImage);

    }

    @Override
    public int getItemCount() {
        return platos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleText, precioText;
        ImageView menuImage;
        ConstraintLayout mainLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.titleText);
            precioText = itemView.findViewById(R.id.precioText);
            menuImage = itemView.findViewById(R.id.productImage);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
