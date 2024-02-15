package com.klanify.snack2go.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.klanify.snack2go.R;
import com.klanify.snack2go.logic.Categoria;

import java.util.ArrayList;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder>{
    ArrayList<Categoria> categorias;

    public CategoriaAdapter(ArrayList<Categoria> categorias) {
        this.categorias = categorias;
    }
    @NonNull
    @Override
    public CategoriaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new CategoriaAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaAdapter.ViewHolder holder, int position) {
        holder.categoryTitle.setText(categorias.get(position).getTitulo());
        String imageURL = "";
        switch (position){
            case 0: {
                imageURL = "cat_1";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background));
                break;
            }
            case 1:{
                imageURL = "cat_2";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background));
                break;
            }
            case 2:{
                imageURL = "cat_3";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background));
                break;
            }
            case 3:{
                imageURL = "cat_4";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background));
                break;
            }
            case 4:{
                imageURL = "cat_5";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background));
                break;
            }
            case 5:{
                imageURL = "cat_2";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background));
                break;
            }
            case 6:{
                imageURL = "cat_1";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background));
                break;
            }
            case 7:{
                imageURL = "cat_3";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background));
                break;
            }
            case 8:{
                imageURL = "cat_4";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background));
                break;
            }

        }
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(imageURL,"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.categoryImage);
    }

    @Override
    public int getItemCount() {
        return this.categorias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView categoryTitle;
        ImageView categoryImage;
        ConstraintLayout mainLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.categoryTitle);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
