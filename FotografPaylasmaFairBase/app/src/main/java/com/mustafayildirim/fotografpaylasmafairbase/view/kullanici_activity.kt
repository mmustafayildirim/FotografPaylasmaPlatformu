package com.mustafayildirim.fotografpaylasmafairbase.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mustafayildirim.fotografpaylasmafairbase.R
import kotlinx.android.synthetic.main.activity_main.*

class kullanici_activity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth=FirebaseAuth.getInstance()
        Login()
        yeniKullanici.setOnClickListener{
            val intent= Intent(this, SingUp::class.java)
            startActivity(intent)
            finish()

        }
        sifremiUnuttum.setOnClickListener {
            startActivity(Intent(applicationContext,ForgetPassword::class.java))
            finish()
        }
        val guncelKullanici=auth.currentUser
        if (guncelKullanici!=null){
            startActivity(Intent(applicationContext,feed_activity::class.java))
            finish()

        }
    }
    private fun Login(){
        girisYap.setOnClickListener {
            if(TextUtils.isEmpty(Email.text.toString())){
                Email.setError("Lütfen E-postanızı Giriniz")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(passWord.text.toString())){
                passWord.setError("Lütfen Şifrenizi Giriniz")
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(Email.text.toString(),passWord.text.toString()).addOnCompleteListener {
                if (it.isSuccessful){
                    startActivity(Intent(this,feed_activity::class.java))
                    finish()
                }
                else{
                    Toast.makeText(this, "Giriş Yapılamadı!Lütfen Tekrar Deneyiniz.", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}


