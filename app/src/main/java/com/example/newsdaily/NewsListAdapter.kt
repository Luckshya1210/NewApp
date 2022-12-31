package com.example.newsdaily

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter (private val listner:NewsItemClicked):RecyclerView.Adapter<NewsViewHolder>() {
    private val items:ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false);
        val viewHolder=NewsViewHolder(view)
        view.setOnClickListener({
            listner.onItemClick(items[viewHolder.adapterPosition]);
        })
        return viewHolder;
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem=items[position];
        holder.titleView.setText(currentItem.title)
        holder.author.text=currentItem.author;
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatednews:ArrayList<News>){
        items.clear();
        items.addAll(updatednews);
        notifyDataSetChanged()
    }

}
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var titleView=itemView.findViewById(R.id.textView1) as TextView;
    var author=itemView.findViewById(R.id.textView) as TextView;
    var image=itemView.findViewById(R.id.imageView) as ImageView;

}
interface NewsItemClicked{
    fun onItemClick(item:News)
}