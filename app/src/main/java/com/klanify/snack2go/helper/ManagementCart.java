package com.klanify.snack2go.helper;

import android.content.Context;
import android.widget.Toast;

import com.klanify.snack2go.interfaces.ChangeNumberItemsListener;
import com.klanify.snack2go.logic.Product;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB database;

    public ManagementCart(Context context) {
        this.context = context;
        this.database = new TinyDB(context);
    }

    public void insertProduct(Product item){
        ArrayList<Product> productList = getListCart();
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

    public ArrayList<Product> getListCart(){
        return database.getListObject("CartList");
    }

    public void plusNumberProduct(ArrayList<Product> products, int position, ChangeNumberItemsListener changeNumberItemsListener){
        int numberInCart = products.get(position).getNumberInCart();
        products.get(position).setNumberInCart(numberInCart + 1);
        database.putListObject("CartList", products);
        changeNumberItemsListener.changed();
    }

    public void minusNumberProduct(ArrayList<Product> products, int position, ChangeNumberItemsListener changeNumberItemsListener){
        int numberInCart = products.get(position).getNumberInCart();

        if(numberInCart == 1){
            products.remove(position);
            Toast.makeText(context,"Producto Removido del Pedido",Toast.LENGTH_SHORT).show();
        }
        else{
            products.get(position).setNumberInCart(numberInCart - 1);
        }

        database.putListObject("CartList", products);
        if(products.size() == 0){

        }
        changeNumberItemsListener.changed();
    }

    public float getSubTotal() {
        ArrayList<Product> products = getListCart();
        float subtotal = 0;

        for(Product element: products){
            subtotal += element.getPrecio() * element.getNumberInCart();
        }
        return subtotal;
    }
}
