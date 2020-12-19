package com.mustafayildirim.fotografpaylasmafairbase.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mustafayildirim.fotografpaylasmafairbase.R
import kotlinx.android.synthetic.main.activity_sing_up.*
import java.util.*


class SingUp : AppCompatActivity() {
        private lateinit var auth: FirebaseAuth
      private  var databaseReference:DatabaseReference?=null
         private var database:FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)
        auth=FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("profil")
        register()

    }
    private fun register(){
        Kaydol.setOnClickListener {
            if(TextUtils.isEmpty(Fullnamee.text.toString())){
                Fullnamee.setError("Lütfen Adınızı Giriniz")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(email.text.toString())){
                email.setError("Lütfen Şifrenizi Giriniz")
                return@setOnClickListener
            }

            else if(TextUtils.isEmpty(Password.text.toString())){
                Password.setError("Lütfen Telefon Numaranızı Giriniz")
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email.text.toString(),Password.text.toString()).addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    val curenntUser = auth.currentUser
                    val currentUserdb = databaseReference?.child(curenntUser?.uid!!)
                    currentUserdb?.child("AdSoyad")?.setValue(Fullnamee.text.toString())
                    currentUserdb?.child("E-posta")?.setValue(email.text.toString())
                    currentUserdb?.child("Şifre")?.setValue(Password.text.toString())
                    Toast.makeText(this, "Kayıt Başarılı ", Toast.LENGTH_LONG).show()

                    startActivity(Intent(applicationContext, feed_activity::class.java))
                    finish()

                }
                else{
                    Toast.makeText(this, "Kayıt Yapılamadı!Lütfen Tekrar Deneyiniz.", Toast.LENGTH_LONG).show()
                    println("kayıtbasarısız")
                }

            }
        }
    }
        fun turnLogin(view: View){
            val intent= Intent(this,
                feed_activity::class.java)
            startActivity(intent)
            finish()

        }



}