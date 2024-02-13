package com.klanify.snack2go.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.klanify.snack2go.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setup()
    }
    private fun setup(){
        val signupbutton = findViewById<Button>(R.id.signupButton)
        val email = findViewById<EditText>(R.id.TextEmailAddress)
        val password = findViewById<EditText>(R.id.TextPassword)
        val label = findViewById<TextView>(R.id.signinLabel)

        title = "Authentication"
        signupbutton.setOnClickListener{
            if(email.text.isNotEmpty() && password.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    email.text.toString(), password.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this,"Registro Exitoso", Toast.LENGTH_SHORT).show()
                        showHome(it.result?.user?.email ?:"", ProviderType.BASIC)
                    }
                    else{
                        showAlert()
                    }
                }
            }
        }

        label.setOnClickListener{
            onBackPressed()
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

    private fun showHome(email:String, provider: ProviderType){
        val homeIntent : Intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(homeIntent)
    }
}