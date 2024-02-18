package com.klanify.snack2go.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.klanify.snack2go.R

class OrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_orders)
        findViewById<ImageView>(R.id.ordersImage).setImageResource(R.mipmap.ordersrojo)
        findViewById<TextView>(R.id.ordersText).setTextColor(getResources().getColor(R.color.primary_color))

        val bundle : Bundle? = intent.extras
        val email : String? = bundle?.getString("email")
        val provider : String? = bundle?.getString("provider")
        setup(email ?:"", provider ?:"")

    }

    private fun setup(email: String, provider: String){
        findViewById<LinearLayout>(R.id.home_button).setOnClickListener {
            val homeIntent = Intent(this, HomeActivity::class.java)
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(homeIntent)
        }

        findViewById<LinearLayout>(R.id.profile_button).setOnClickListener {
            showProfile(email,provider)
        }

        findViewById<LinearLayout>(R.id.notificacion_button).setOnClickListener{
            val notificacionIntent = Intent(this, NotificationActivity::class.java)
            notificacionIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(notificacionIntent)
        }

        findViewById<ImageView>(R.id.backImageOrders).setOnClickListener {
            onBackPressed()
        }

        findViewById<Button>(R.id.makeOrderButton).setOnClickListener {

        }
    }

    fun onShoppingcartButtonClick(view: View) {
        val shoppingcartIntent = Intent(this, ShoppingcartActivity::class.java)
        shoppingcartIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(shoppingcartIntent)
    }

    private fun showProfile(email:String, provider: String){
        val profileIntent : Intent = Intent(this, ProfileActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider)
        }
        profileIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(profileIntent)
    }

}