package com.klanify.snack2go.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.klanify.snack2go.R
import com.klanify.snack2go.adaptor.CartAdapter
import com.klanify.snack2go.helper.ManagementCart
import com.klanify.snack2go.interfaces.ChangeNumberItemsListener

class ShoppingcartActivity : AppCompatActivity() {
    companion object{
        var emptyCart :Boolean = true
    }
    private var adapterShoppingCart: RecyclerView.Adapter<*>? = null
    private lateinit var recyclerViewCartList: RecyclerView
    private lateinit var managementCart : ManagementCart
    private lateinit var subTotalPriceText : TextView
    private lateinit var taxText : TextView
    private lateinit var totalPriceText : TextView
    private lateinit var emptyText : TextView
    private var tax :Double = 0.0
    private lateinit var scrollViewShoppingCart :ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoppingcart)
        managementCart = ManagementCart(this)
        showContent()

        val bundle : Bundle? = intent.extras
        val email : String? = bundle?.getString("email")
        val provider : String? = bundle?.getString("provider")
        //val productos: ArrayList<Producto>? = intent.getSerializableExtra("productos") as? ArrayList<Producto>
        setup(email ?:"", provider ?:"")

        initView()
        initList()
        calculatePrices()
    }

    override fun onResume() {
        super.onResume()
        showContent()

    }
    private fun initView(){
        if(!emptyCart) {
            subTotalPriceText = findViewById(R.id.subTotalPriceText)
            taxText = findViewById(R.id.taxText)
            totalPriceText = findViewById(R.id.totalPriceText)
            scrollViewShoppingCart = findViewById(R.id.scrollViewShoppingCart)
            recyclerViewCartList = findViewById(R.id.cartList_RecyclerView)
        }
    }

    private fun initList(){
        if(!emptyCart) {
            val linearLayoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recyclerViewCartList.setLayoutManager(linearLayoutManager)
            adapterShoppingCart = //revisar si el managementCart.listCART Esta lleno o vacio
                CartAdapter(managementCart.listCart, this, object : ChangeNumberItemsListener {
                    override fun changed() {
                        calculatePrices()
                    }
                })
            recyclerViewCartList.adapter = adapterShoppingCart
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculatePrices(){
        if(!emptyCart) {
            val percentIVA = 0.12
            val subtotal = ((managementCart.subTotal) * 100).toInt()
            val truncatedSubtotal = subtotal / 100.0f
            val iva = ((managementCart.subTotal * percentIVA) * 100).toInt()
            val truncatedIVA = iva / 100.0f
            val total = ((truncatedSubtotal + truncatedIVA)*100).toInt()
            val truncatedTotal = total/100.0f


            subTotalPriceText.text = "${'$'}${' '}${truncatedSubtotal}"
            taxText.text = "${'$'}${' '}${truncatedIVA}"
            totalPriceText.text = "${'$'}${' '}${truncatedTotal}"
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

        findViewById<ImageView>(R.id.backImageShoppingcart).setOnClickListener {
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

    public fun showNoProductsCartContent(){
        findViewById<LinearLayout>(R.id.noProductsLayout).visibility = View.VISIBLE
        findViewById<ScrollView>(R.id.scrollViewShoppingCart).visibility = View.INVISIBLE
    }

    public fun showScrollViewShoppingCart(){
        findViewById<LinearLayout>(R.id.noProductsLayout).visibility = View.GONE
        findViewById<ScrollView>(R.id.scrollViewShoppingCart).visibility = View.VISIBLE
    }

    private fun showContent(){
        if (managementCart.listCart.size != 0){
            emptyCart = false
        }
        else{
            emptyCart = true
        }
        if(emptyCart){
            showNoProductsCartContent()
        }
        else {
            showScrollViewShoppingCart()
        }
    }
}