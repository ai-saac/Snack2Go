package com.klanify.snack2go.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
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
        val displayName : String? = bundle?.getString("displayName")
        val userPhoto : String? = bundle?.getString("profilePhoto")
        val provider : String? = bundle?.getString("provider")

        setup(email ?:"", displayName ?:"",userPhoto ?:"", provider ?:"")
    }
    private fun setup(email: String, displayName: String,userPhoto: String,provider: String){
        title = "Profile"
        findViewById<TextView>(R.id.emailText).text = email
        findViewById<TextView>(R.id.displayNameProfile).text = displayName

        val profilePhoto = findViewById<ImageView>(R.id.accountImageProfile)
        Glide.with(this)
            .load(Uri.parse(userPhoto))
            .placeholder(R.mipmap.account) // Opcional: imagen de carga mientras se descarga la imagen
            .error(R.mipmap.account) // Opcional: imagen de error si la carga de la imagen falla
            .into(profilePhoto)

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

        findViewById<LinearLayout>(R.id.notificacion_button).setOnClickListener{
            val notificacionIntent = Intent(this, NotificationActivity::class.java)
            notificacionIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(notificacionIntent)
        }

        findViewById<ImageView>(R.id.backImageProfile).setOnClickListener {
            onBackPressed()
        }
    }

    fun onShoppingcartButtonClick(view: View) {
        val shoppingcartIntent = Intent(this, ShoppingcartActivity::class.java)
        shoppingcartIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(shoppingcartIntent)
    }

}