package com.klanify.snack2go.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.klanify.snack2go.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        findViewById<ImageView>(R.id.profileImage).setImageResource(R.mipmap.profilerojo)
        findViewById<TextView>(R.id.profileText).setTextColor(getResources().getColor(R.color.primary_color))
        val bundle : Bundle? = intent.extras
        val email : String? = bundle?.getString("email")
        val provider : String? = bundle?.getString("provider")
        setup(email ?:"", provider ?:"")
    }
    private fun setup(email: String, provider: String){
        title = "Profile"
        findViewById<TextView>(R.id.emailText).text = email
        findViewById<Button>(R.id.logout_button).setOnClickListener{
            lateinit var prefs : SharedPreferences.Editor
            prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            if(provider == ProviderType.FACEBOOK.name){
                LoginManager.getInstance().logOut()
            }
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    fun onHomeButtonClick(view: View) {
        val homeIntent = Intent(this, HomeActivity::class.java)
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(homeIntent)
    }

    fun onNotificacionButtonClick(view: View) {
        val notificacionIntent = Intent(this, NotificacionActivity::class.java)
        notificacionIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(notificacionIntent)
    }

    fun onOrdersButtonClick(view: View) {
        val ordersIntent = Intent(this, OrdersActivity::class.java)
        ordersIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(ordersIntent)
    }

    fun onShoppingcartButtonClick(view: View) {
        val shoppingcartIntent = Intent(this, ShoppingcartActivity::class.java)
        shoppingcartIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(shoppingcartIntent)
    }
}