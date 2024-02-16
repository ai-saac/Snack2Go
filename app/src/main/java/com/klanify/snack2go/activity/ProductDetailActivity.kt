package com.klanify.snack2go.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.klanify.snack2go.R
import com.klanify.snack2go.logic.Producto

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var productDName:TextView
    private lateinit var plusButton :ImageView
    private lateinit var minusButton :ImageView
    private lateinit var productPrice :TextView
    private lateinit var productDescription :TextView
    private lateinit var quantityText :TextView
    private lateinit var productImage :ImageView
    private lateinit var addToOrderButton :Button
    private lateinit var productObject : Producto
    private var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productdetail)
        setup()
        getBundle()

    }

    @SuppressLint("SetTextI18n")
    private fun getBundle(){
        productObject = intent.getSerializableExtra("object") as Producto
        val drawableResourceId = this.resources.getIdentifier(productObject.imagen, "drawable", this.packageName)
        Glide.with(this)
            .load(drawableResourceId)
            .into(productImage)
        productDName.text = productObject.nombre
        productPrice.text = "${'$'}${' '}${productObject.precio}"
        quantityText.text = quantity.toString()
        plusButton.setOnClickListener{
            quantity = quantity + 1
            quantityText.text = quantity.toString()
        }
        minusButton.setOnClickListener {
            if(quantity > 1){
                quantity = quantity - 1
                quantityText.text = quantity.toString()
            }
            else{
                Toast.makeText(this,"Pedido Mínimo 1", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun setup(){
        productDName = findViewById(R.id.productNameText)
        plusButton = findViewById(R.id.plusButton)
        minusButton = findViewById(R.id.minusButton)
        productPrice = findViewById(R.id.priceText)
        productDescription = findViewById(R.id.descripcionText)
        quantityText = findViewById(R.id.quantityText)
        productImage = findViewById(R.id.productDImage)
        addToOrderButton = findViewById(R.id.addToOrderButton)


    }

}