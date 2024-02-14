package com.klanify.snack2go.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.klanify.snack2go.R

enum class ProviderType{
    BASIC,
    GOOGLE,
    FACEBOOK
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<ImageView>(R.id.homeIcon).setImageResource(R.mipmap.homerojo)
        findViewById<TextView>(R.id.homeText).setTextColor(getResources().getColor(R.color.primary_color))
        val bundle : Bundle? = intent.extras
        val email : String? = bundle?.getString("email")
        val provider : String? = bundle?.getString("provider")
        setup(email ?:"", provider ?:"")

        lateinit var prefs : SharedPreferences.Editor
            prefs = getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
            prefs.putString("email", email)
            prefs.putString("provider", provider)
            prefs.apply()
    }

    private fun setup(email: String, provider: String){
        title = "Home"
        findViewById<LinearLayout>(R.id.profile_button).setOnClickListener{
            showProfile(email,provider)
        }

        """findViewById<TextView>(R.id.username).text = email
        findViewById<TextView>(R.id.TextProvider).text = provider
        findViewById<Button>(R.id.logoutButton).setOnClickListener{
            lateinit var prefs : SharedPreferences.Editor
            prefs = getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            if(provider == ProviderType.FACEBOOK.name){
                LoginManager.getInstance().logOut()
            }
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }"""
    }
    override fun onBackPressed() {
        // No hacemos nada aquí para evitar que el usuario regrese a la actividad anterior
    }
    private fun showProfile(email:String, provider: String){
        val profileIntent : Intent = Intent(this, ProfileActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider)
        }
        startActivity(profileIntent)
    }

    /*fun onProfileButtonClick(view: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(intent)
    }*/

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