package com.mustafayildirim.fotografpaylasmafairbase.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mustafayildirim.fotografpaylasmafairbase.R
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_sing_up.*

class ForgetPassword : AppCompatActivity() {
    lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        mAuth= FirebaseAuth.getInstance()
        sifirlamayaGonder.setOnClickListener {
            val emeil=emaileGonder.text.toString()
            if(emeil.isEmpty()){
                emaileGonder.setError("Lütfen E-postanızı Giriniz")
                return@setOnClickListener
        }
            forgetPassword(emeil)


    }

}
    fun LogineDon(view:View){
        startActivity(Intent(applicationContext,kullanici_activity::class.java))
        finish()}

    private fun forgetPassword(emeil: String) {
        mAuth.sendPasswordResetEmail(emeil).addOnCompleteListener {
            if (it.isComplete){
                startActivity(Intent(applicationContext,kullanici_activity::class.java))
                Toast.makeText(this, "Lütfen E-postanızı Kontrol Ediniz Ve Şifrenizi Sıfırlayanız.", Toast.LENGTH_LONG).show()
                finish()
            }
        }



        }


    }
