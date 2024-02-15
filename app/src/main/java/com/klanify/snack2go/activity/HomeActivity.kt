package com.klanify.snack2go.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.klanify.snack2go.logic.Producto
import com.klanify.snack2go.adaptor.ProductoAdapter
import java.lang.reflect.Field
import com.klanify.snack2go.R
import com.klanify.snack2go.adaptor.CategoriaAdapter
import com.klanify.snack2go.adaptor.MenuAdapter
import com.klanify.snack2go.logic.Categoria
import com.klanify.snack2go.logic.Plato


enum class ProviderType{
    BASIC,
    GOOGLE,
    FACEBOOK
}
class HomeActivity : AppCompatActivity() {
    private var adapterProducts: RecyclerView.Adapter<*>? = null
    private var adapterMenu: RecyclerView.Adapter<*>? = null
    private var adapterCategories: RecyclerView.Adapter<*>? = null

    private lateinit var recyclerViewCategoryList: RecyclerView
    private lateinit var recyclerViewProductsList: RecyclerView
    private lateinit var recyclerViewMenuList: RecyclerView
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

        recyclerViewCategory()
        recyclerViewMenu()
        recyclerViewProducts()
    }

    private fun setup(email: String, provider: String){
        title = "Home"
        findViewById<LinearLayout>(R.id.profile_button).setOnClickListener{
            showProfile(email,provider)
        }

        showPopupMenu(provider)
        """findViewById<TextView>(R.id.username).text = email
        findViewById<TextView>(R.id.TextProvider).text = provider
        """
    }
    override fun onBackPressed() {
        // No hacemos nada aquí para evitar que el usuario regrese a la actividad anterior
    }
    private fun showProfile(email:String, provider: String){
        val profileIntent : Intent = Intent(this, ProfileActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider)
        }
        profileIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(profileIntent)
    }

    /*fun onProfileButtonClick(view: View) {
        val intent = Intent(this, ProfileActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider)
        }
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

    fun showPopupMenu(provider: String) {
        val imageView :ImageView= findViewById(R.id.profile_photo)
        val popupMenu = PopupMenu(applicationContext, imageView)
        popupMenu.inflate(R.menu.contextual_menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_favoritos -> {
                    val favoritesIntent = Intent(this, ShoppingcartActivity::class.java)
                    favoritesIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(favoritesIntent)
                    true
                }
                R.id.action_logout -> {
                    lateinit var prefs : SharedPreferences.Editor
                    prefs = getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
                    prefs.clear()
                    prefs.apply()

                    if(provider == ProviderType.FACEBOOK.name){
                        LoginManager.getInstance().logOut()
                    }
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, LoginActivity::class.java))

                    true
                }
                else -> false
            }
        }

        imageView.setOnLongClickListener {
            try {
                val popup : Field = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu :Any? = popup.get(popupMenu)
                if (menu != null) {
                    menu.javaClass
                        .getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                        .invoke(menu,true)
                }
            }catch (e :Exception){
                e.printStackTrace()
            }finally {
                popupMenu.show()
            }
            true
        }
    }

    fun recyclerViewCategory(){
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategoryList = findViewById<RecyclerView>(R.id.categories_recyclerView)
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager)

        val categorias = ArrayList<Categoria>()
        categorias.add(Categoria("Pizza", "cat_1"))
        categorias.add(Categoria("Burguers", "cat_2"))
        categorias.add(Categoria("Hot Dogs", "cat_3"))
        categorias.add(Categoria("Bebidas", "cat_4"))
        categorias.add(Categoria("Bocaditos", "cat_5"))
        categorias.add(Categoria("Snacks", "cat_1"))
        categorias.add(Categoria("Empanadas", "cat_2"))
        categorias.add(Categoria("Platos", "cat_3"))
        categorias.add(Categoria("Frituras", "cat_1"))

        adapterCategories = CategoriaAdapter(categorias)
        recyclerViewCategoryList.setAdapter(adapterCategories)

    }

    fun recyclerViewMenu(){
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewMenuList = findViewById<RecyclerView>(R.id.menu_recyclerView)
        recyclerViewMenuList.setLayoutManager(linearLayoutManager)

        val platos = ArrayList<Plato>()
        platos.add(Plato("Pollo\nBrostizado", "pop_2", 2.50F))
        platos.add(Plato("Seco\nde Pollo", "pop_1", 2.75F))
        platos.add(Plato("Arroz\nMenestra y Carne", "pop_3", 2.00F))
        platos.add(Plato("Estofado\nde Carne", "pop_2", 2.25F))
        platos.add(Plato("Enrollado\nde Atún", "pop_1", 2.00F))
        platos.add(Plato("Chaulafán\n", "pop_3", 1.5F))
        platos.add(Plato("Milanesa\nde Pollo", "pop_2", 3.00F))
        platos.add(Plato("Patacones\ncon Queso", "pop_3", 1.00F))
        platos.add(Plato("Estofado\nde Pollo", "pop_1", 2.50F))

        adapterMenu = MenuAdapter(platos)
        recyclerViewMenuList.setAdapter(adapterMenu)
    }

    fun recyclerViewProducts(){
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewProductsList = findViewById<RecyclerView>(R.id.product_recyclerView)
        recyclerViewProductsList.setLayoutManager(linearLayoutManager)

        val productos = ArrayList<Producto>()
        productos.add(Producto("Coca Cola\nPersonal", "pop_1", 0.35F))
        productos.add(Producto("Hamburguesa\nSencilla", "pop_2", 0.75F))
        productos.add(Producto("Empanada\nde Carne", "pop_3", 0.40F))
        productos.add(Producto("Sanduche\nde Pollo", "pop_1", 0.45F))
        productos.add(Producto("Hamburguesa\ncon Huevo", "pop_2", 0.85F))
        productos.add(Producto("Empanada\nde Pizza", "pop_1", 0.6F))
        productos.add(Producto("Hambuerguesa\ndoble Carne", "pop_3", 1.5F))
        productos.add(Producto("Empanada\nde Queso", "pop_2", 0.35F))
        productos.add(Producto("Salchipapa\n", "pop_1", 0.75F))

        adapterProducts = ProductoAdapter(productos)
        recyclerViewProductsList.setAdapter(adapterProducts)
    }


}