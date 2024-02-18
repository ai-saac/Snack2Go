package com.klanify.snack2go.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.klanify.snack2go.R

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_notificacions)
        findViewById<ImageView>(R.id.notificacionImage).setImageResource(R.mipmap.reminderrojo)
        findViewById<TextView>(R.id.notificacionText).setTextColor(getResources().getColor(R.color.primary_color))

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

        findViewById<LinearLayout>(R.id.profile_button).setOnClickListener{
            showProfile(email,provider)
        }

        findViewById<LinearLayout>(R.id.orders_button).setOnClickListener {
            val ordersIntent = Intent(this, OrdersActivity::class.java)
            ordersIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(ordersIntent)
        }

        findViewById<ImageView>(R.id.backImageNoShoppingcart).setOnClickListener {
            onBackPressed()
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

    fun onBackButtonClick(){
        onBackPressed()
    }
}