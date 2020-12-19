package com.mustafayildirim.fotografpaylasmafairbase.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mustafayildirim.fotografpaylasmafairbase.R
import com.mustafayildirim.fotografpaylasmafairbase.model.post
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycle_row.view.*

class feed_recycle_adapter(val postList:ArrayList<post>) :RecyclerView.Adapter<feed_recycle_adapter.PostHolder>(){
    class PostHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
    val infilater=LayoutInflater.from(parent.context)
        val view=infilater.inflate(R.layout.recycle_row,parent,false)
        return PostHolder(view)

    }

    override fun getItemCount(): Int {
    return postList.size

    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
    holder.itemView.recycleview_kullaniciEmaili.text=postList[position].kullaniciEmaili
        holder.itemView.recycleview_kullaniciyorumu.text=postList[position].kullaniciyorumu
        Picasso.get().load(postList[position].gorselUrl).into(holder.itemView.recyle_imageview)



    }
}