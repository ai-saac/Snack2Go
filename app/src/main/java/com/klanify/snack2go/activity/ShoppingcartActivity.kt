package com.klanify.snack2go.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.klanify.snack2go.R
import com.klanify.snack2go.helper.ManagementCart

class ShoppingcartActivity : AppCompatActivity() {
    private var adapterShoppingCart: RecyclerView.Adapter<*>? = null
    private lateinit var recyclerViewCartList: RecyclerView
    private lateinit var managementCart : ManagementCart
    private lateinit var subTotalPriceText : TextView
    private lateinit var taxText : TextView
    private lateinit var totalPriceText : TextView
    private lateinit var emptyText : TextView
    private var tax :Double = 0.0
    private lateinit var scrollViewShoppingCart :ScrollView
    private var emptyCart :Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(emptyCart){
            setContentView(R.layout.activity_no_shoppingcart)
        }
        else {
            setContentView(R.layout.activity_shoppingcart)
        }

        val bundle : Bundle? = intent.extras
        val email : String? = bundle?.getString("email")
        val provider : String? = bundle?.getString("provider")
        setup(email ?:"", provider ?:"")

        managementCart = ManagementCart(this)
        initView()

    }

    private fun initView(){
        if(!emptyCart) {
            subTotalPriceText = findViewById(R.id.subTotalPriceText)
            taxText = findViewById(R.id.taxText)
            totalPriceText = findViewById(R.id.totalPriceText)
            scrollViewShoppingCart = findViewById(R.id.scrollViewShoppingCart)
        }
    }

    private fun setup(email: String, provider: String){
        findViewById<LinearLayout>(R.id.home_button).setOnClickListener {
            val homeIntent = Intent(this, HomeActivity::class.java)
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(homeIntent)
        }

        findViewById<LinearLayout>(R.id.orders_button).setOnClickListener {
            val ordersIntent = Intent(this, OrdersActivity::class.java)
            ordersIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(ordersIntent)
        }

        findViewById<LinearLayout>(R.id.profile_button).setOnClickListener{
            showProfile(email,provider)
        }

        findViewById<LinearLayout>(R.id.notificacion_button).setOnClickListener{
            val notificacionIntent = Intent(this, NotificationActivity::class.java)
            notificacionIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(notificacionIntent)
        }

        findViewById<ImageView>(R.id.backImageNoShoppingcart).setOnClickListener {
            onBackPressed()
        }

        findViewById<FloatingActionButton>(R.id.cart_button).setOnClickListener {
            Toast.makeText(this,"Ya estás en el carrito",Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProfile(email:String, provider: String){
        val profileIntent : Intent = Intent(this, ProfileActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider)
        }
        profileIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(profileIntent)
    }



    fun onShoppingcartButtonClick() {
        Toast.makeText(this,"Estás en el carrito",Toast.LENGTH_SHORT).show()
    }
}