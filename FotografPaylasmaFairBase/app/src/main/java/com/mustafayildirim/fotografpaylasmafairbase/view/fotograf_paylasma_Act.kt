package com.mustafayildirim.fotografpaylasmafairbase.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mustafayildirim.fotografpaylasmafairbase.R
import kotlinx.android.synthetic.main.activity_fotograf_paylasma_.*
import java.util.*

class fotograf_paylasma_Act : AppCompatActivity() {
    var secilenGorsel: Uri? = null
    var secilenBitmap: Bitmap? = null
    private lateinit var storage: FirebaseStorage
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fotograf_paylasma_)
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
    }
    fun paylas(view: View) {
        //depo işlemleri:Storage
        val uuid = UUID.randomUUID()
        val gorselİsmi = "${uuid}.jpg"
        val refarence = storage.reference
        val gorselRef = refarence.child("images").child(gorselİsmi)
        if (secilenGorsel != null) {
            gorselRef.putFile(secilenGorsel!!).addOnSuccessListener { taskSnapshot ->
                val yuklenengorref =
                    FirebaseStorage.getInstance().reference.child("images").child(gorselİsmi)
                yuklenengorref.downloadUrl.addOnSuccessListener { uri ->
                    val dowloanduri = uri.toString()
                    val guncelkullaniciEmail = auth.currentUser!!.email.toString()
                    val kullaniciYorumu = editText.text.toString()
                    val tarih = Timestamp.now()
                    val postHashMap = hashMapOf<String, Any>()
                    postHashMap.put("gorselurl", dowloanduri)
                    postHashMap.put("kullaniciEmaili", guncelkullaniciEmail)
                    postHashMap.put("kullaniciyorumu", kullaniciYorumu)
                    postHashMap.put("tarih", tarih)
                    database.collection("post").add(postHashMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent=Intent(this,
                                feed_activity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }

        }
        fun gorselSec(view: View) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                //izni almamısız.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )
            } else {
                //izin zaten varsa yapılacaklar
                val galeriInt =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriInt, 2)
            }
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            if (requestCode == 1) {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //izin verilmişse yapılacaklar
                    val galeriInt = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galeriInt, 2)

                }
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
                secilenGorsel = data.data
                if (secilenGorsel != null) {
                    if (Build.VERSION.SDK_INT >= 28) {
                        val source =
                            ImageDecoder.createSource(this.contentResolver, secilenGorsel!!)
                        secilenBitmap = ImageDecoder.decodeBitmap(source)
                        imageView.setImageBitmap(secilenBitmap)
                    } else {
                        secilenBitmap =
                            MediaStore.Images.Media.getBitmap(this.contentResolver, secilenGorsel)
                        imageView.setImageBitmap(secilenBitmap)
                    }
                }
            }
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
