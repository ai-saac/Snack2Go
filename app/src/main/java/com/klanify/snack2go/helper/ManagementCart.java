package com.klanify.snack2go.helper;

import android.content.Context;
import android.widget.Toast;

import com.klanify.snack2go.logic.Producto;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB database;

    public ManagementCart(Context context) {
        this.context = context;
        this.database = new TinyDB(context);
    }

    public void insertProduct(Producto item){
        ArrayList<Producto> productList = getListCart();
        boolean existAlready = false;
        int n = 0;
        for(int i = 0; i<productList.size();i++){
            if(productList.get(i).getNombre().equals(item.getNombre())){
                existAlready = true;
                n = i;
                break;
            }
            if(existAlready){
                productList.get(i).setNumberInCart(item.getNumberInCart());
            }
            else{
                productList.add(item);
            }
            database.putObject("CartList", productList);
            Toast.makeText(context,"Agregado al Pedido",Toast.LENGTH_SHORT);
        }
    }

    public ArrayList<Producto> getListCart(){
        return database.getListObject("CartList");
    }

}
