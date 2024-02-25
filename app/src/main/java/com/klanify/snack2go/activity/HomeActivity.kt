package com.klanify.snack2go.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.klanify.snack2go.logic.Product
import com.klanify.snack2go.adaptor.ProductoAdapter
import java.lang.reflect.Field
import com.klanify.snack2go.R
import com.klanify.snack2go.adaptor.CategoryAdapter
import com.klanify.snack2go.adaptor.MenuAdapter
import com.klanify.snack2go.logic.Category
import com.klanify.snack2go.logic.Dish


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

    private lateinit var products: ArrayList<Product>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<ImageView>(R.id.homeIcon).setImageResource(R.mipmap.homerojo)
        findViewById<TextView>(R.id.homeText).setTextColor(getResources().getColor(R.color.primary_color))

        val bundle : Bundle? = intent.extras
        val email : String? = bundle?.getString("email")
        val displayName: String? = bundle?.getString("displayName")
        val userPhoto: String? = bundle?.getString("profilePhoto")
        val provider : String? = bundle?.getString("provider")

        setup(email ?:"", displayName ?:"",userPhoto ?:"",provider ?:"")

        lateinit var prefs : SharedPreferences.Editor
            prefs = getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
            prefs.putString("email", email)
            prefs.putString("displayName",displayName)
            prefs.putString("userPhoto",userPhoto)
            prefs.putString("provider", provider)
            prefs.apply()

        recyclerViewCategory()
        recyclerViewMenu()
        recyclerViewProducts()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setup(email: String, displayName: String, userPhoto: String,provider: String){
        title = "Home"

        findViewById<TextView>(R.id.displayName_Home).text = displayName

        val profilePhoto = findViewById<ImageView>(R.id.profile_photo)
        Glide.with(this)
            .load(Uri.parse(userPhoto))
            .placeholder(R.mipmap.account) // Opcional: imagen de carga mientras se descarga la imagen
            .error(R.mipmap.account) // Opcional: imagen de error si la carga de la imagen falla
            .into(profilePhoto)

        findViewById<LinearLayout>(R.id.profile_button).setOnClickListener{
            showProfile(email,displayName,userPhoto,provider)
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

        findViewById<FloatingActionButton>(R.id.cart_button).setOnClickListener {
            showShoppingcart(email, provider)
        }

        showPopupMenu(provider)
        initProductsList();
    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    private fun showProfile(email:String, displayName: String, userPhoto: String, provider: String){
        val profileIntent : Intent = Intent(this, ProfileActivity::class.java).apply {
            putExtra("email",email)
            putExtra("displayName",displayName)
            putExtra("profilePhoto",userPhoto)
            putExtra("provider",provider)
        }
        profileIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(profileIntent)
    }

    private fun showShoppingcart(email:String, provider: String) {
        val shoppincartIntent : Intent = Intent(this, ShoppingcartActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider)
        }
        shoppincartIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(shoppincartIntent)
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

        val categorias = ArrayList<Category>()
        categorias.add(Category("Pizza", "cat_1"))
        categorias.add(Category("Burguers", "cat_2"))
        categorias.add(Category("Hot Dogs", "cat_3"))
        categorias.add(Category("Bebidas", "cat_4"))
        categorias.add(Category("Bocaditos", "cat_5"))
        categorias.add(Category("Snacks", "cat_1"))
        categorias.add(Category("Empanadas", "cat_2"))
        categorias.add(Category("Platos", "cat_3"))
        categorias.add(Category("Frituras", "cat_1"))

        adapterCategories =
            CategoryAdapter(categorias)
        recyclerViewCategoryList.setAdapter(adapterCategories)

    }

    fun recyclerViewMenu(){
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewMenuList = findViewById<RecyclerView>(R.id.menu_recyclerView)
        recyclerViewMenuList.setLayoutManager(linearLayoutManager)

        val dishes = ArrayList<Dish>()
        dishes.add(
            Dish(
                "Pollo\nBrostizado",
                "i1",
                "Arroz, papas, leche, mantequilla, sal, pollo broaster con especias.",
                "2.50".toFloat()
            )
        )
        dishes.add(
            Dish(
                "Seco\nde Pollo",
                "i2",
                "Pollo, arroz, cebolla, tomate, ajo, culantro, salsa de ají amarillo, sal, pimienta, aceite.",
                2.75F
            )
        )
        dishes.add(
            Dish(
                "Arroz\nMenestra y Carne",
                "i3",
                "Arroz, menestra (arvejas, zanahoria, choclo), carne (pollo, res o cerdo), cebolla, ajo, culantro, salsa de tomate, sal, pimienta, aceite vegetal.",
                2.00F
            )
        )
        dishes.add(
            Dish(
                "Estofado\nde Carne",
                "i4",
                "Carne (res, cerdo o pollo), papas, zanahorias, arvejas, tomates, cebolla, ajo, laurel, vino tinto, caldo o agua, sal, pimienta negra.",
                2.25F
            )
        )
        dishes.add(
            Dish(
                "Enrollado\nde Atún",
                "i5",
                "Atún enlatado, mayonesa, lechuga, tomate, cebolla, tortilla de trigo.",
                2.00F
            )
        )
        dishes.add(
            Dish(
                "Chaulafán\n",
                "i6",
                "Arroz, cebolla, ajo, pollo, carne molida de res, brócoli, coliflor, zanahoria, salsa de soya, sal, pimienta.",
                1.5F
            )
        )
        dishes.add(
            Dish(
                "Milanesa\nde Pollo",
                "i8",
                "Pechuga de pollo, huevo, harina, pan rallado, sal, pimienta, aceite para freír.",
                3.00F
            )
        )
        dishes.add(
            Dish(
                "Patacones\ncon Queso",
                "i7",
                "Plátanos verdes, aceite para freír, queso blanco salado (por ejemplo queso costeño), cilantro picado, cebolla morada, limón.",
                1.00F
            )
        )
        dishes.add(
            Dish(
                "Estofado\nde Pollo",
                "i9",
                "Pollo en trozos, papas, zanahorias, cebolla, ajo, tomates, perejil, laurel, vino blanco, caldo de pollo, sal, pimienta.",
                2.50F
            )
        )
        dishes.add(
            Dish(
                "Encebollado\n",
                "i11",
                "Pescado (albacora o picudo), cebolla, ajo, tomate, cilantro, naranjilla, sal y pimienta.",
                2.50F
            )
        )

        adapterMenu = MenuAdapter(dishes)
        recyclerViewMenuList.setAdapter(adapterMenu)
    }

    fun recyclerViewProducts(){
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewProductsList = findViewById<RecyclerView>(R.id.product_recyclerView)
        recyclerViewProductsList.setLayoutManager(linearLayoutManager)
        adapterProducts = ProductoAdapter(products)
        recyclerViewProductsList.setAdapter(adapterProducts)
    }

    fun initProductsList(){
        products = ArrayList<Product>()
        products.add(
            Product(
                "Coca Cola\nPersonal",
                "i13",
                "Agua carbonatada, jarabe de alta fructosa, colorante caramelo, ácido fosfórico, extractos naturales, cafeína.",
                0.35F
            )
        )
        products.add(
            Product(
                "Hamburguesa\nSencilla",
                "i14",
                "Pan, carne de res molida, ketchup, mostaza, mayonesa, tomate, lechuga, cebolla, pepinillos, queso.",
                0.75F
            )
        )
        products.add(
            Product(
                "Empanada\nde Carne",
                "i10",
                "Masa: harina, agua, sal, grasa (manteca, margarina o aceite). Relleno: carne molida, cebolla, condimentos (sal, pimienta, pimentón, etc.), masa de empanada rellena y cocida al horno o frita.",
                0.40F
            )
        )
        products.add(
            Product(
                "Sanduche\nde Pollo",
                "i12",
                "Pan, pechuga de pollo, lechuga, tomate, mayonesa, sal y pimienta.",
                0.45F
            )
        )
        products.add(
            Product(
                "Hamburguesa\ncon Huevo",
                "i15",
                "Pan, carne de res molida, huevo frito, queso, lechuga, tomate, cebolla, ketchup, mostaza, mayonesa, pepinillos.",
                0.85F
            )
        )
        products.add(
            Product(
                "Hambuerguesa\ndoble Carne",
                "i19",
                "Pan, dos hamburguesas de carne de res molida, queso, lechuga, tomate, cebolla, ketchup, mostaza, mayonesa, pepinillos.",
                1.5F
            )
        )
        products.add(
            Product(
                "Empanada\nde Queso",
                "i17",
                "Masa: harina, agua, sal, grasa (manteca, margarina o aceite). Relleno: queso (por ejemplo mozzarella, cheddar), masa de empanada rellena con queso y cocida al horno o frita.",
                0.35F
            )
        )
        products.add(
            Product(
                "Salchipapa\n",
                "i26",
                "Salchichas, papas fritas, mayonesa, ketchup, cebolla picada, ajo picado, sal, aceite para freír.",
                0.75F
            )
        )
        products.add(
            Product(
                "Empanada\nde pollo",
                "i21",
                "Masa: harina, agua, sal, grasa (manteca o aceite vegetal). Relleno: pollo desmenuzado, cebolla, ajo, culantro, sal, pimienta, masa de empanada rellena con el guiso de pollo y cocida al horno o frita.",
                0.75F
            )
        )
        products.add(
            Dish(
                "Pollo\nBrostizado",
                "i1",
                "Arroz, papas, leche, mantequilla, sal, pollo broaster con especias.",
                "2.50".toFloat()
            )
        )
        products.add(
            Product(
                "Choripan\n",
                "i29",
                "Pan, chorizo ecuatoriano, curtido de cebolla, tomate, ají casero.",
                0.75F
            )
        )
        products.add(
            Product(
                "Churros\n",
                "i28",
                "Harina, agua, huevos, sal, aceite vegetal para freír, azúcar glass para espolvorear.",
                0.75F
            )
        )
        products.add(
            Dish(
                "Arroz\nMenestra y Carne",
                "i3",
                "Arroz, menestra (arvejas, zanahoria, choclo), carne (pollo, res o cerdo), cebolla, ajo, culantro, salsa de tomate, sal, pimienta, aceite vegetal.",
                2.00F
            )
        )
        products.add(
            Product(
                "Agua\nmedio litro",
                "i24",
                "Agua de mesa procesada de alta calidad",
                0.75F
            )
        )
        products.add(
            Dish(
                "Milanesa\nde Pollo",
                "i8",
                "Pechuga de pollo, huevo, harina, pan rallado, sal, pimienta, aceite para freír.",
                3.00F
            )
        )
        products.add(
            Product(
                "Hot Dogs\n",
                "i22",
                "Pan de hot dog, salchichas, kétchup, mostaza, chucrut, cebolla curtida.",
                0.75F
            )
        )
        products.add(
            Product(
                "Empanada Ranchera\n",
                "i18",
                "Masa: harina, agua, sal, grasa (manteca o aceite vegetal). Relleno: carne molida, papas, cebolla, ajo, ají, comino, masa de empanada rellena y cocida al horno o frita.",
                0.75F
            )
        )
        products.add(
            Product(
                "Chifles\n",
                "i27",
                "Plátano verde, aceite vegetal para freír, sal.",
                0.75F
            )
        )
        products.add(
            Product(
                "Pepsi\nPersonal",
                "i23",
                "Agua carbonatada, jarabe de alta fructosa, colorante caramelo, ácido fosfórico, cafeína, aromatizantes.",
                0.75F
            )
        )
        products.add(
            Dish(
                "Seco\nde Pollo",
                "i2",
                "Pollo, arroz, cebolla, tomate, ajo, culantro, salsa de ají amarillo, sal, pimienta, aceite.",
                2.75F
            )
        )
        products.add(
            Dish(
                "Estofado\nde Carne",
                "Carne (res, cerdo o pollo), papas, zanahorias, arvejas, tomates, cebolla, ajo, laurel, vino tinto, caldo o agua, sal, pimienta negra.",
                "i4",
                2.25F
            )
        )
        products.add(
            Product(
                "Empanada\nde Pizza",
                "i16",
                "Masa: harina, agua, sal, grasa (manteca o aceite vegetal). Relleno: salsa de tomate, queso mozzarella, pepperoni, champiñones, masa de empanada rellena y cocida al horno o frita",
                0.6F
            )
        )
        products.add(
            Dish(
                "Enrollado\nde Atún",
                "i5",
                "Atún enlatado, mayonesa, lechuga, tomate, cebolla, tortilla de trigo.",
                2.00F
            )
        )
        products.add(
            Dish(
                "Chaulafán\n",
                "i6",
                "Arroz, cebolla, ajo, pollo, carne molida de res, brócoli, coliflor, zanahoria, salsa de soya, sal, pimienta.",
                1.5F
            )
        )
        products.add(
            Dish(
                "Pizza\nPeperoni",
                "i20",
                "Masa de pizza, salsa de tomate, pepperoni, queso mozzarella, orégano.",
                1.5F
            )
        )
        products.add(
            Dish(
                "Patacones\ncon Queso",
                "i7",
                "Plátanos verdes, aceite para freír, queso blanco salado (por ejemplo queso costeño), cilantro picado, cebolla morada, limón.",
                1.00F
            )
        )
        products.add(
            Dish(
                "Estofado\nde Pollo",
                "i9",
                "Pollo en trozos, papas, zanahorias, cebolla, ajo, tomates, perejil, laurel, vino blanco, caldo de pollo, sal, pimienta.",
                2.50F
            )
        )
        products.add(
            Dish(
                "Encebollado\n",
                "i11",
                "Pescado (albacora o picudo), cebolla, ajo, tomate, cilantro, naranjilla, sal y pimienta.",
                2.50F
            )
        )
        products.add(
            Dish(
                "Pizza\nItaliana",
                "i25",
                "Masa de pizza, salsa de tomate, queso mozzarella, jamón, champiñones, aceitunas negras, albahaca fresca, orégano.",
                1.5F
            )
        )

    }

}