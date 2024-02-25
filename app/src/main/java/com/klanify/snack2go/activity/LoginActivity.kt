package com.klanify.snack2go.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.klanify.snack2go.R
import java.net.URI

class LoginActivity : AppCompatActivity() {
    private val GOOGLESIGNIN = 100
    private val callbackmanager = CallbackManager.Factory.create()
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        //Thread.sleep(2000)
        setTheme(R.style.Base_Theme_Snack2Go)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setup()
        sesion()

    }

    override fun onStart(){
        super.onStart()
        var layout = findViewById<LinearLayout>(R.id.loginLayout).visibility
        layout = View.VISIBLE

    }

    //Función para registar las credenciales de inicio de sesión
    //Qué permite que cuando el usario deje de usar la aplicación vuelva a la pantalla de inicio
    //y no a la de login.
    private fun sesion(){
        lateinit var prefs : SharedPreferences
        prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)

        val email :String? = prefs.getString("email", null)
        val displayName :String? = prefs.getString("displayName", null)
        val userPhoto :String? = prefs.getString("userPhoto",null)
        val provider :String? = prefs.getString("provider", null)

        if(email != null && displayName != null && provider != null ){
            //Inicio de la Home Activity
            if (userPhoto != null) {
                showHome(email, displayName,userPhoto,ProviderType.valueOf(provider))
            }
            else{
                showHome(email, displayName,"",ProviderType.valueOf(provider))
            }
        }
    }

    private fun setup(){
        val signinbutton = findViewById<Button>(R.id.signinButton)
        val googlebutton = findViewById<Button>(R.id.logout_button)
        val facebookbutton = findViewById<Button>(R.id.fbbutton)
        val email = findViewById<EditText>(R.id.TextEmailAddress)
        val password = findViewById<EditText>(R.id.TextPassword)
        val label = findViewById<TextView>(R.id.signupLabel)

        title = "Authentication"
        //Inicio de Sesión con Usuario y Contraseña
        signinbutton.setOnClickListener{
            if(email.text.isNotEmpty() && password.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    email.text.toString(), password.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this,"Inicio Exitoso", Toast.LENGTH_SHORT).show()
                        showHome(it.result?.user?.email ?:"", it.result.user?.displayName ?:"","",ProviderType.BASIC)
                    }
                    else{
                        showAlert()
                    }
                }
            }
            else{
                Toast.makeText(this,"Ingrese su email o contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        //Inicio con Usuario de Google
        googlebutton.setOnClickListener{
            val googleConf :GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(this, googleConf)
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent,GOOGLESIGNIN)

        }

        //Inicio con Usuario de Facebook
        facebookbutton.setOnClickListener{
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
            LoginManager.getInstance().registerCallback(callbackmanager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult) {
                        result?.let{
                            val token = it.accessToken
                            val credential :AuthCredential = FacebookAuthProvider.getCredential(token.token)
                            FirebaseAuth.getInstance().signInWithCredential(credential)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        //Funcion que inicia la Home Activity
                                        val photo = try {
                                            it.result?.user?.photoUrl.toString()
                                        }catch (e :Exception){
                                            ""
                                        }
                                        showHome(it.result?.user?.email ?: "", it.result?.user?.displayName ?:"",photo,ProviderType.FACEBOOK)
                                    } else {
                                        showAlert()
                                    }
                                }
                        }
                    }

                    override fun onCancel() {

                    }

                    override fun onError(error: FacebookException) {
                        showAlert()
                    }
                })
        }

        //Acción del texto de registro de usuario
        label.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Error al autenticar usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //Función para iniciar la Home Activity enviando el usuario y tipo de autenticación
    private fun showHome(email:String, displayName:String, photo :String,provider: ProviderType){
        val homeIntent : Intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email",email)
            putExtra("displayName", displayName)
            putExtra("profilePhoto",photo)
            putExtra("provider",provider.name)
        }
        startActivity(homeIntent)
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //Se ejecuta on la autenticación de Facebook
        callbackmanager.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
        //Se ejecuta con la autenticación de Google
        if(requestCode == GOOGLESIGNIN){
            val task :Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account :GoogleSignInAccount? = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credential :AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                //
                                val email = account.email ?: ""
                                val displayName = account.displayName ?:""
                                val photo = try {
                                    account.photoUrl.toString()
                                }catch (e : Exception){
                                    ""
                                }
                                showHome(email,displayName, photo,ProviderType.GOOGLE)

                            } else {
                                showAlert()
                            }
                        }
                }
            }catch (e: ApiException){
                showAlert()
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true)
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Presiona otra vez para minimizar la aplicación", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000) // Tiempo en milisegundos para presionar dos veces
    }

}