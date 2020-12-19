package com.mustafayildirim.fotografpaylasmafairbase.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mustafayildirim.fotografpaylasmafairbase.Adapter.feed_recycle_adapter
import com.mustafayildirim.fotografpaylasmafairbase.R
import com.mustafayildirim.fotografpaylasmafairbase.model.post
import kotlinx.android.synthetic.main.activity_feed_activity.*

class feed_activity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database:FirebaseFirestore
    private lateinit var recycleviewAdapter:feed_recycle_adapter
    var postList=ArrayList<post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_activity)
        auth=FirebaseAuth.getInstance()
        database= FirebaseFirestore.getInstance()
        verilerial()
        var layoutMenager=LinearLayoutManager(this)
        recyclerView.layoutManager=layoutMenager
        recycleviewAdapter= feed_recycle_adapter(postList)
        recyclerView.adapter=recycleviewAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.secenekler_menusu,menu)
        return super.onCreateOptionsMenu(menu)
    }
        fun verilerial(){
            database.collection("post").orderBy("tarih",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
                if (error!=null){
                    Toast.makeText(this,error.localizedMessage, Toast.LENGTH_LONG).show()
                }
                else{
                    if (value!=null){
                        if (!value.isEmpty){
                          val docuuments=  value.documents
                            postList.clear()
                            for (document in docuuments){
                              val email=  document.get("kullaniciEmaili") as String
                                val kullaniciyorum=document.get("kullaniciyorumu")as String
                                val gorselUrl=document.get("gorselurl")as String
                                val indirilenpost=
                                    post(
                                        email,
                                        kullaniciyorum,
                                        gorselUrl
                                    )
                                postList.add(indirilenpost)
                            }
                            recycleviewAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

        }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId== R.id.fotograf_paylas){
            val intent=Intent(this,
                fotograf_paylasma_Act::class.java)
            startActivity(intent)
            finish()

        }
        else if(item.itemId== R.id.cikis_yap){
            auth.signOut()
            val intent=Intent(this,
                kullanici_activity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}