package com.klanify.snack2go.helper;

import android.content.Context;
import android.widget.Toast;

import com.klanify.snack2go.activity.ShoppingcartActivity;
import com.klanify.snack2go.interfaces.ChangeNumberItemsListener;
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
        for(int i = 0; i<productList.size();i++) {
            if (productList.get(i).getNombre().equals(item.getNombre())) {
                existAlready = true;
                n = i;
                break;
            }
        }
        if(existAlready){
            productList.get(n).setNumberInCart(item.getNumberInCart());
        }
        else{
            productList.add(item);
        }
        database.putListObject("CartList", productList);
        Toast.makeText(context,"Agregado al Pedido",Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Producto> getListCart(){
        return database.getListObject("CartList");
    }

    public void plusNumberProduct(ArrayList<Producto> productos, int position, ChangeNumberItemsListener changeNumberItemsListener){
        int numberInCart = productos.get(position).getNumberInCart();
        productos.get(position).setNumberInCart(numberInCart + 1);
        database.putListObject("CartList", productos);
        changeNumberItemsListener.changed();
    }

    public void minusNumberProduct(ArrayList<Producto> productos, int position, ChangeNumberItemsListener changeNumberItemsListener){
        int numberInCart = productos.get(position).getNumberInCart();

        if(numberInCart == 1){
            productos.remove(position);
            Toast.makeText(context,"Producto Removido del Pedido",Toast.LENGTH_SHORT).show();
        }
        else{
            productos.get(position).setNumberInCart(numberInCart - 1);
        }

        database.putListObject("CartList", productos);
        if(productos.size() == 0){

        }
        changeNumberItemsListener.changed();
    }

    public float getSubTotal() {
        ArrayList<Producto> productos = getListCart();
        float subtotal = 0;

        for(Producto element: productos){
            subtotal += element.getPrecio() * element.getNumberInCart();
        }
        return subtotal;
    }
}
